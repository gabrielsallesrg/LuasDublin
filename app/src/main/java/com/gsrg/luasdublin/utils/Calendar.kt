package com.gsrg.luasdublin.utils

import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.text.format.DateFormat
import com.gsrg.luasdublin.core.utils.ICalendar
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class Calendar
@Inject constructor(@ApplicationContext applicationContext: Context) : ICalendar {

    val context = applicationContext

    override fun hour(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        } else {
            java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        }
    }

    override fun minute(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance().get(Calendar.MINUTE)
        } else {
            java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE)
        }
    }

    override fun time(): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance().time.time
        } else {
            java.util.Calendar.getInstance().time.time
        }
    }

    /**
     * It is afternoon if is between 12:01 and 23:59
     */
    override fun isAfternoon(): Boolean {
        return (hour() > 12 || (hour() == 12 && minute() > 0))
    }

    override fun formattedHourAndMinute(time: Long): String {
        val dateFormat = DateFormat.getTimeFormat(context)
        val pattern = (dateFormat as SimpleDateFormat).toLocalizedPattern()
        return DateFormat.format(pattern, Date(time)).toString()
    }
}