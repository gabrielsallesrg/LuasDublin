package com.gsrg.luasdublin.domain.repository

import com.gsrg.luasdublin.core.models.Forecast
import com.gsrg.luasdublin.core.models.UpdateTime
import com.gsrg.luasdublin.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface IForecastRepository {

    fun getForecast(stop: String, isAfternoon: Boolean, date: Long): Flow<Result<List<Forecast>>>
    fun getUpdatedTime(): Flow<UpdateTime?>
}