package com.gsrg.luasdublin.domain.api

import com.gsrg.luasdublin.domain.model.ForecastResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface LuasApiService {

    @GET("get.ashx?action=forecast&encrypt=false")
    fun luasTimes(@Query("stop") stop: String): Observable<ForecastResponse>
}