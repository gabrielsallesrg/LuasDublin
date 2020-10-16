package com.gsrg.luasdublin.database.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecast: Forecast)

    @Query("SELECT * FROM forecastTable")
    suspend fun selectAll(): List<Forecast>?

    @Query("DELETE FROM forecastTable")
    suspend fun clearTable()
}