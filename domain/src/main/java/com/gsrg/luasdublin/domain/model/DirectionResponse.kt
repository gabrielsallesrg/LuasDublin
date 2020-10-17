package com.gsrg.luasdublin.domain.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "direction", strict = false)
data class DirectionResponse( //TODO adapt to XML

    @field:Attribute(name = "name")
    @param:Attribute(name = "name")
    val name: String,

    @field:ElementList(name = "tram", inline = true)
    @param:ElementList(name = "tram", inline = true)
    val tramList: List<TramResponse>
)