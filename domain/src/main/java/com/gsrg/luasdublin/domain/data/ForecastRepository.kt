package com.gsrg.luasdublin.domain.data

import com.gsrg.luasdublin.domain.api.LuasApiService
import javax.inject.Inject

class ForecastRepository
@Inject constructor(private val luasApiService: LuasApiService) : IForecastRepository