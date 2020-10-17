package com.gsrg.luasdublin.di

import com.gsrg.luasdublin.utils.Calendar
import com.gsrg.luasdublin.utils.ICalendar
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class CalendarModule {

    @Binds
    abstract fun bindCalendar(calendar: Calendar): ICalendar
}