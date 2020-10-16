package com.gsrg.luasdublin.database.updatetime

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "updateTimeTable")
data class UpdateTime(
    @PrimaryKey
    val date: Long
)