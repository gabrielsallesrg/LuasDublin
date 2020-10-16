package com.gsrg.luasdublin.database.forecast

import androidx.room.Entity

@Entity(tableName = "forecastTable", primaryKeys = ["destination", "dueMinutes"])
data class Forecast(
    val destination: String,
    val dueMinutes: String
)