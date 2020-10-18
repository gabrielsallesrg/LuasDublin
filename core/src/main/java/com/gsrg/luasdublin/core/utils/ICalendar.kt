package com.gsrg.luasdublin.core.utils

interface ICalendar {

    fun hour(): Int
    fun minute(): Int
    fun time(): Long
    fun isAfternoon(): Boolean
    fun formattedHourAndMinute(time: Long = time()): String
}