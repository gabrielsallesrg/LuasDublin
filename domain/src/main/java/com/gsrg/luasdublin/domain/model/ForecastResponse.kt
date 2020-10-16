package com.gsrg.luasdublin.domain.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root
data class ForecastResponse( //TODO adapt to XML
    @field:Attribute(name = "Inbound", required = false)
    @param:Attribute(name = "Inbound", required = false)
    val inbound: List<ForecastDetailsResponse>? = null,

    @field:Attribute(name = "Outbound", required = false)
    @param:Attribute(name = "Outbound", required = false)
    val outbound: List<ForecastDetailsResponse>? = null
)