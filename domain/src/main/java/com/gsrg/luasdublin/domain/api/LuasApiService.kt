package com.gsrg.luasdublin.domain.api

import com.gsrg.luasdublin.domain.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LuasApiService {

    @GET("get.ashx?action=forecast&encrypt=false")
    suspend fun luasTimes(@Query("stop") stop: String): Response<ForecastResponse>
}