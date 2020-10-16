package com.gsrg.luasdublin.ui.fragments.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForecastViewModel : ViewModel() {

    val forecastListLiveData = MutableLiveData<List<Int>>() //TODO change from Int to ForecastItem
    val lastUpdateAtLiveData = MutableLiveData<String>()

    fun requestForecastList() {
        forecastListLiveData.value = listOf(1, 2, 3, 4, 5, 6)
        lastUpdateAtLiveData.value = "25:62"
    }
}