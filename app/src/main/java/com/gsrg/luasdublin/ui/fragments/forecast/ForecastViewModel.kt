package com.gsrg.luasdublin.ui.fragments.forecast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.luasdublin.Event
import com.gsrg.luasdublin.core.utils.ICalendar
import com.gsrg.luasdublin.core.utils.Result
import com.gsrg.luasdublin.database.forecast.Forecast
import com.gsrg.luasdublin.domain.repository.IForecastRepository
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
                repository.getForecast(calendar).collect { result: Result<List<Forecast>> ->
                    when (result) {
                        is Result.Success -> {
                            forecastListLiveData.value = result.data
                            requestEventLiveData.value = Event(result)
                            updateTime()
                        }
                        is Result.Loading -> {
                            result.data?.let { forecastListLiveData.value = it }
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
}