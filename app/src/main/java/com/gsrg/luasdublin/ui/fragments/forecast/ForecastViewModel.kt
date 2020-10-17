package com.gsrg.luasdublin.ui.fragments.forecast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.luasdublin.Event
import com.gsrg.luasdublin.database.ILuasDatabase
import com.gsrg.luasdublin.database.forecast.Forecast
import com.gsrg.luasdublin.domain.api.Result
import com.gsrg.luasdublin.domain.data.IForecastRepository
import com.gsrg.luasdublin.domain.model.DirectionResponse
import com.gsrg.luasdublin.domain.model.TramResponse
import com.gsrg.luasdublin.domain.utils.TAG
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random

class ForecastViewModel
@ViewModelInject constructor(
    private val repository: IForecastRepository,
    private val database: ILuasDatabase
) : ViewModel() {

    private var disposables = CompositeDisposable()
    private var forecastObservable: Observable<List<Forecast>>? = null

    val forecastListLiveData = MutableLiveData<Event<Result<List<Forecast>>>>()
    val lastUpdateAtLiveData = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun requestForecastList() {
        if (forecastObservable != null) {
            disposables.clear()
        }
        viewModelScope.launch {
            requestForecastsFromDB()
            forecastListLiveData.value = Event(Result.Loading)
            forecastObservable = repository.getForecastByStop(getStopAbbreviationName())
                .map {
                    val directionList = getCorrectTramList(it.directionList ?: emptyList())
                    val resultList = ArrayList<Forecast>()
                    for (direction in directionList) {
                        resultList.add(Forecast(destination = direction.destination, dueMinutes = direction.dueMins))
                    }
                    resultList.toList()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

            forecastObservable?.subscribe(object : Observer<List<Forecast>> {
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: List<Forecast>) {
                    viewModelScope.launch {
                        storeForecastInDB(t)
                        requestForecastsFromDB()
                    }
                    //TODO delete this next line
                    lastUpdateAtLiveData.value = "42:42" //TODO update with the right time
                }

                override fun onError(e: Throwable) {
                    Timber.tag(TAG()).e(e)
                    forecastListLiveData.value = Event(Result.Error(Exception(e), e.message ?: "Something went wrong"))
                }

                override fun onComplete() {
                    Timber.tag(TAG()).d("forecastObservable: onComplete")
                }

            })
        }
    }

    private fun getCorrectTramList(directionList: List<DirectionResponse>): List<TramResponse> {
        val directionName = if (isItAfternoon()) "Inbound" else "Outbound"
        for (direction in directionList) {
            if (direction.name == directionName) {
                return direction.tramList
            }
        }
        return emptyList()
    }

    private fun getStopAbbreviationName(): String {
        return if (isItAfternoon()) "sti" else "mar"
    }

    private fun isItAfternoon(): Boolean {
        //TODO check if it is 12:01 – 23:59
        return Random.nextBoolean()
    }

    private suspend fun storeForecastInDB(forecastList: List<Forecast>) {
        database.forecastDao().clearTable()
        database.forecastDao().insertAll(forecastList)
    }

    private suspend fun requestForecastsFromDB() {
        val forecastList: List<Forecast> = database.forecastDao().selectAll() ?: emptyList()
        if (forecastList.isNotEmpty()) {
            forecastListLiveData.value = Event(Result.Success(data = forecastList))
        }
    }
}