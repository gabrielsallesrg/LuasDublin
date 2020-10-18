package com.gsrg.luasdublin.network.repository

import com.gsrg.luasdublin.domain.model.ForecastResponse
import com.gsrg.luasdublin.domain.repository.IForecastRepository
import com.gsrg.luasdublin.network.api.LuasApiService
import io.reactivex.Observable
import javax.inject.Inject

class ForecastRepository
@Inject constructor(private val luasApiService: LuasApiService) : IForecastRepository {

    override fun getForecastByStop(stop: String): Observable<ForecastResponse> {
        return luasApiService.luasTimes(stop = stop)
    }

}