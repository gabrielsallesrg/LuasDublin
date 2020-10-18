package com.gsrg.luasdublin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [com.gsrg.luasdublin.core.models.Forecast::class, com.gsrg.luasdublin.core.models.UpdateTime::class],
    version = 1,
    exportSchema = false
)
abstract class LuasDatabase : RoomDatabase(), ILuasDatabase {

    companion object {

        @Volatile
        private var INSTANCE: LuasDatabase? = null

        fun getInstance(context: Context): LuasDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LuasDatabase::class.java,
                "LuasDublin.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}