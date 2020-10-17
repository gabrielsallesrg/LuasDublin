package com.gsrg.luasdublin.domain.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "tram", strict = false)
data class TramResponse(
    @field:Attribute(name = "destination")
    @param:Attribute(name = "destination")
    val destination: String,

    @field:Attribute(name = "dueMins")
    @param:Attribute(name = "dueMins")
    val dueMins: String
)