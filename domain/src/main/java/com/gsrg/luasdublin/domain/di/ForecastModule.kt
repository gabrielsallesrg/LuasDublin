package com.gsrg.luasdublin.domain.di

import com.gsrg.luasdublin.domain.data.ForecastRepository
import com.gsrg.luasdublin.domain.data.IForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class ForecastModule {

    @Binds
    abstract fun bindForecastRepository(forecastRepository: ForecastRepository): IForecastRepository
}