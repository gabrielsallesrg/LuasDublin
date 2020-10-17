package com.gsrg.luasdublin.ui.fragments.forecast

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.luasdublin.Event
import com.gsrg.luasdublin.database.ILuasDatabase
import com.gsrg.luasdublin.database.forecast.Forecast
import com.gsrg.luasdublin.database.updatetime.UpdateTime
import com.gsrg.luasdublin.domain.api.Result
import com.gsrg.luasdublin.domain.data.IForecastRepository
import com.gsrg.luasdublin.domain.model.DirectionResponse
import com.gsrg.luasdublin.domain.model.TramResponse
import com.gsrg.luasdublin.domain.utils.TAG
import com.gsrg.luasdublin.utils.ICalendar
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ForecastViewModel
@ViewModelInject constructor(
    private val repository: IForecastRepository,
    private val database: ILuasDatabase,
    private val calendar: ICalendar
) : ViewModel() {

    private var disposables = CompositeDisposable()
    private var forecastObservable: Observable<List<Forecast>>? = null

    val requestEventLiveData = MutableLiveData<Event<Result<Boolean>>>()
    val forecastListLiveData = MutableLiveData<List<Forecast>>()
    val lastUpdateAtLiveData = MutableLiveData<String>()

    private var requestForecastJob: Job? = null
    private var firstRun = true

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun requestForecastList(firstRun: Boolean = false) {
        if (!firstRun || (firstRun && this.firstRun)) {
            this.firstRun = false
            if (forecastObservable != null) {
                disposables.clear()
            }
            requestForecastJob?.cancel()
            requestForecastJob = viewModelScope.launch {
                requestForecastsFromDB()
                requestUpdateTimeFromDB()
                requestEventLiveData.value = Event(Result.Loading)
                var time: Long = 0
                forecastObservable = repository.getForecastByStop(getStopAbbreviationName())
                    .map {
                        val directionList = getCorrectTramList(it.directionList ?: emptyList())
                        time = calendar.time()
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
                            storeUpdateTimeInDB(time)
                            requestUpdateTimeFromDB()
                            requestEventLiveData.value = Event(Result.Success(data = true))
                        }
                    }

                    override fun onError(e: Throwable) {
                        Timber.tag(TAG()).e(e)
                        requestEventLiveData.value = Event(Result.Error(Exception(e), e.message ?: "Something went wrong"))
                    }

                    override fun onComplete() {
                        Timber.tag(TAG()).d("forecastObservable: onComplete")
                    }

                })
            }
        }

    }

    private fun getCorrectTramList(directionList: List<DirectionResponse>): List<TramResponse> {
        val directionName = if (isAfternoon()) "Inbound" else "Outbound"
        for (direction in directionList) {
            if (direction.name == directionName) {
                return direction.tramList ?: emptyList()
            }
        }
        return emptyList()
    }

    private fun getStopAbbreviationName(): String {
        return if (isAfternoon()) "sti" else "mar"
    }

    /**
     * It is afternoon if is between 12:01 and 23:59
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isAfternoon(): Boolean {
        val hour = calendar.hour()
        val minute = calendar.minute()
        return (hour > 12 || (hour == 12 && minute > 0))
    }

    private suspend fun storeForecastInDB(forecastList: List<Forecast>) {
        database.forecastDao().clearTable()
        database.forecastDao().insertAll(forecastList)
    }

    private suspend fun requestForecastsFromDB() {
        val forecastList: List<Forecast> = database.forecastDao().selectAll() ?: emptyList()
        forecastListLiveData.value = forecastList
    }

    private suspend fun storeUpdateTimeInDB(time: Long) {
        database.updateTimeDao().clearTable()
        database.updateTimeDao().insert(UpdateTime(time))
    }

    private suspend fun requestUpdateTimeFromDB() {
        val updateTime: UpdateTime? = database.updateTimeDao().select()
        if (updateTime != null) {
            lastUpdateAtLiveData.value = calendar.formattedHourAndMinute(updateTime.date)
        }
    }
}