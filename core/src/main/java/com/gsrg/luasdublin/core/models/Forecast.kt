package com.gsrg.luasdublin.core.models

import androidx.room.Entity

@Entity(tableName = "forecastTable", primaryKeys = ["destination", "dueMinutes"])
data class Forecast(
    val destination: String,
    val dueMinutes: String
)