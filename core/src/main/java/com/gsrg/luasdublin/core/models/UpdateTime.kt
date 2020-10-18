package com.gsrg.luasdublin.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "updateTimeTable")
data class UpdateTime(
    @PrimaryKey
    val date: Long
)