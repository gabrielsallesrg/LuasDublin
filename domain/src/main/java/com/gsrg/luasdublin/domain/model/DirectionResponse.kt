package com.gsrg.luasdublin.domain.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "direction", strict = false)
data class DirectionResponse(

    @field:Attribute(name = "name")
    @param:Attribute(name = "name")
    val name: String,

    @field:ElementList(name = "tram", inline = true, required = false)
    @param:ElementList(name = "tram", inline = true, required = false)
    val tramList: List<TramResponse>? = null
)