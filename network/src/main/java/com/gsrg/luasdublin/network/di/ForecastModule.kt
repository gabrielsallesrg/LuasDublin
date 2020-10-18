package com.gsrg.luasdublin.network.di

import com.gsrg.luasdublin.domain.repository.IForecastRepository
import com.gsrg.luasdublin.network.repository.ForecastRepository
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