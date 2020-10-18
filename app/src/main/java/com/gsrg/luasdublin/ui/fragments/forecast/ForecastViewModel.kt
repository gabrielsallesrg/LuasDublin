package com.gsrg.luasdublin.ui.fragments.forecast

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.luasdublin.Event
import com.gsrg.luasdublin.core.models.Forecast
import com.gsrg.luasdublin.core.utils.Result
import com.gsrg.luasdublin.domain.repository.IForecastRepository
import com.gsrg.luasdublin.utils.ICalendar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ForecastViewModel
@ViewModelInject constructor(
    private val repository: IForecastRepository,
    private val calendar: ICalendar
) : ViewModel() {

    val requestEventLiveData = MutableLiveData<Event<Result<*>>>()
    val forecastListLiveData = MutableLiveData<List<Forecast>>()
    val lastUpdateAtLiveData = MutableLiveData<String>()

    private var firstRun = true

    fun requestForecastList(firstRun: Boolean = false) {
        if (!firstRun || (firstRun && this.firstRun)) {
            this.firstRun = false
            viewModelScope.launch {
                repository.getForecast(
                    stop = getStopAbbreviationName(),
                    isAfternoon = isAfternoon(),
                    date = calendar.time()
                ).collect { result: Result<List<Forecast>> ->
                    when (result) {
                        is Result.Success -> {
                            forecastListLiveData.value = result.data
                            requestEventLiveData.value = Event(result)
                            updateTime()
                        }
                        is Result.Loading -> {
                            result.data?.let {
                                forecastListLiveData.value = it
                                updateTime()
                            }
                            requestEventLiveData.value = Event(result)
                        }
                        is Result.Error -> {
                            requestEventLiveData.value = Event(result)
                        }
                    }
                }
            }
        }
    }

    private fun updateTime() {
        viewModelScope.launch {
            repository.getUpdatedTime().collect {
                if (it != null) {
                    lastUpdateAtLiveData.value = calendar.formattedHourAndMinute(it.date)
                }
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getStopAbbreviationName(): String {
        return if (isAfternoon()) "sti" else "mar"
    }

    /**
     * It is afternoon if is between 12:01 and 23:59
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isAfternoon(): Boolean {
        return (calendar.hour() > 12 || (calendar.hour() == 12 && calendar.minute() > 0))
    }
}