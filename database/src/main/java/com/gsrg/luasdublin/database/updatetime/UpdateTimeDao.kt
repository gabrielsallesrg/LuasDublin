package com.gsrg.luasdublin.database.updatetime

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UpdateTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(updateTime: com.gsrg.luasdublin.core.models.UpdateTime)

    @Query("SELECT * FROM updateTimeTable LIMIT 1")
    suspend fun select(): com.gsrg.luasdublin.core.models.UpdateTime?

    @Query("DELETE FROM updateTimeTable")
    suspend fun clearTable()
}