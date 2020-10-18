package com.gsrg.luasdublin.network.repository

import com.gsrg.luasdublin.core.models.Forecast
import com.gsrg.luasdublin.core.models.UpdateTime
import com.gsrg.luasdublin.core.utils.Result
import com.gsrg.luasdublin.core.utils.TAG
import com.gsrg.luasdublin.database.ILuasDatabase
import com.gsrg.luasdublin.domain.api.LuasApiService
import com.gsrg.luasdublin.domain.model.DirectionResponse
import com.gsrg.luasdublin.domain.model.ForecastResponse
import com.gsrg.luasdublin.domain.model.TramResponse
import com.gsrg.luasdublin.domain.repository.IForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class ForecastRepository
@Inject constructor(
    private val luasApiService: LuasApiService,
    private val database: ILuasDatabase
) : IForecastRepository {

    override fun getForecast(stop: String, isAfternoon: Boolean, date: Long): Flow<Result<List<Forecast>>> = flow {
        emit(Result.Loading(requestForecastsFromDB()))
        requestForecastFromApi(stop = stop)
            .flowOn(Dispatchers.IO)
            .map { response: Result<ForecastResponse> -> mapForecastResponseToListForecast(response, isAfternoon) }
            .collect {
                when (it) {
                    is Result.Success -> {
                        storeForecastInDB(it.data)
                        storeUpdateTimeInDB(date)
                        emit(Result.Success(requestForecastsFromDB()))
                    }
                    else -> {
                        Timber.tag(TAG()).e((it as Result.Error).message)
                        emit(it)
                    }
                }
            }
    }

    override fun getUpdatedTime(): Flow<UpdateTime?> = flow {
        emit(requestUpdateTimeFromDB())
    }


    private fun requestForecastFromApi(stop: String): Flow<Result<ForecastResponse>> = flow {
        emit(try {
            luasApiService.luasTimes(stop = stop).run {
                Timber.tag(this@ForecastRepository.TAG()).d("%s -> %s", message(), body())
                if (isSuccessful && body() != null) {
                    Result.Success(data = body()!!)
                } else {
                    Result.Error(Exception("Missing data"))
                }
            }
        } catch (e: UnknownHostException) {
            Timber.tag(TAG()).e(e)
            Result.Error(e, "Something went wrong. Check your internet connection.")
        } catch (e: Exception) {
            Timber.tag(TAG()).e(e)
            Result.Error(e)
        })
    }

    private fun mapForecastResponseToListForecast(forecastResponse: Result<ForecastResponse>, isAfternoon: Boolean): Result<List<Forecast>> {
        return when (forecastResponse) {
            is Result.Success -> {
                val directionList = getCorrectTramList(isAfternoon, forecastResponse.data.directionList ?: emptyList()) //TODO fix isAfternoon
                val resultList = ArrayList<Forecast>()
                for (direction in directionList) {
                    resultList.add(Forecast(destination = direction.destination, dueMinutes = direction.dueMins))
                }
                Result.Success(resultList.toList())
            }
            is Result.Error -> {
                forecastResponse
            }
            is Result.Loading -> {
                Result.Error(Exception("This should never happen"))
            }
        }
    }

    private fun getCorrectTramList(isAfternoon: Boolean, directionList: List<DirectionResponse>): List<TramResponse> {
        val directionName = if (isAfternoon) "Inbound" else "Outbound"
        for (direction in directionList) {
            if (direction.name == directionName) {
                return direction.tramList ?: emptyList()
            }
        }
        return emptyList()
    }

    /**
     * From here until the end is about doing requests to DB
     * and updating the DB
     */
    private suspend fun storeForecastInDB(forecastList: List<Forecast>) {
        database.forecastDao().clearTable()
        database.forecastDao().insertAll(forecastList)
    }

    private suspend fun requestForecastsFromDB(): List<Forecast> {
        return database.forecastDao().selectAll() ?: emptyList()
    }

    private suspend fun storeUpdateTimeInDB(time: Long) {
        database.updateTimeDao().clearTable()
        database.updateTimeDao().insert(UpdateTime(time))
    }

    private suspend fun requestUpdateTimeFromDB(): UpdateTime? {
        return database.updateTimeDao().select()
    }
}