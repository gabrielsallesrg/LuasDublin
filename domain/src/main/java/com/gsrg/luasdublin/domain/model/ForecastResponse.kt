package com.gsrg.luasdublin.domain.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "stopInfo", strict = false)
data class ForecastResponse(

    @field:Element(name = "message", required = false)
    @param:Element(name = "message", required = false)
    val message: String,

    @field:ElementList(name = "direction", inline = true)
    @param:ElementList(name = "direction", inline = true)
    val directionList: List<DirectionResponse>? = null
)