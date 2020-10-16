package com.gsrg.luasdublin.domain.model

data class ForecastResponse( //TODO adapt to XML
    val inbound: List<ForecastDetailsResponse>?,
    val outbound: List<ForecastDetailsResponse>?
)