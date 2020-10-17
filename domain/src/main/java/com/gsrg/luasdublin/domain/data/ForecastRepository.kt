package com.gsrg.luasdublin.domain.data

import com.gsrg.luasdublin.domain.api.LuasApiService
import com.gsrg.luasdublin.domain.model.ForecastResponse
import io.reactivex.Observable
import javax.inject.Inject

class ForecastRepository
@Inject constructor(private val luasApiService: LuasApiService) : IForecastRepository {

    override fun getForecastByStop(stop: String): Observable<ForecastResponse> {
        return luasApiService.luasTimes(stop = stop)
    }

}