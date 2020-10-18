package com.gsrg.luasdublin.domain.repository

import com.gsrg.luasdublin.core.utils.ICalendar
import com.gsrg.luasdublin.core.utils.Result
import com.gsrg.luasdublin.database.forecast.Forecast
import com.gsrg.luasdublin.database.updatetime.UpdateTime
import kotlinx.coroutines.flow.Flow

interface IForecastRepository {

    fun getForecast(calendar: ICalendar): Flow<Result<List<Forecast>>>
    fun getUpdatedTime(): Flow<UpdateTime?>
}