package com.gsrg.luasdublin.database.di

import android.content.Context
import com.gsrg.luasdublin.database.ILuasDatabase
import com.gsrg.luasdublin.database.LuasDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideCodeWarsDatabase(@ApplicationContext applicationContext: Context): ILuasDatabase {
        return LuasDatabase.getInstance(applicationContext)
    }
}