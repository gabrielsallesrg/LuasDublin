package com.gsrg.luasdublin.database.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gsrg.luasdublin.core.models.Forecast

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(forecastList: List<Forecast>)

    @Query("SELECT * FROM forecastTable")
    suspend fun selectAll(): List<Forecast>?

    @Query("DELETE FROM forecastTable")
    suspend fun clearTable()
}