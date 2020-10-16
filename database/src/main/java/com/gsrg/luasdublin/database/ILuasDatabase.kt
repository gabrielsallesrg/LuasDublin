package com.gsrg.luasdublin.database

import com.gsrg.luasdublin.database.forecast.ForecastDao
import com.gsrg.luasdublin.database.updatetime.UpdateTimeDao

interface ILuasDatabase {

    fun updateTimeDao(): UpdateTimeDao
    fun forecastDao(): ForecastDao
}