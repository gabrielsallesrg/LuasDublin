package com.gsrg.luasdublin.domain.data

import com.gsrg.luasdublin.domain.model.ForecastResponse
import io.reactivex.Observable

interface IForecastRepository {

    fun getForecastByStop(stop: String): Observable<ForecastResponse>
}