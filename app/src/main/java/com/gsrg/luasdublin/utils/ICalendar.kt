package com.gsrg.luasdublin.utils

interface ICalendar {

    fun hour(): Int
    fun minute(): Int
    fun time(): Long
    fun formattedHourAndMinute(time: Long = time()): String
}