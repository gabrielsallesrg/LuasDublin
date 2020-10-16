package com.gsrg.luasdublin.domain.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root
data class ForecastDetailsResponse( //TODO adapt to XML
    @field:Attribute(name = "destination")
    @param:Attribute(name = "destination")
    val destination: String,

    @field:Attribute(name = "dueMins")
    @param:Attribute(name = "dueMins")
    val dueMins: String
)