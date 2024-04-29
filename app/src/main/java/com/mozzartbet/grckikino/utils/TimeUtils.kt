package com.mozzartbet.grckikino.utils

import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

object TimeUtils {

    const val SECOND = 1000
    const val MINUTE = 60 * SECOND
    const val HOUR = 60 * MINUTE
    const val DAY = 24 * HOUR

    const val API_DATE_FORMAT = "yyyy-MM-dd"

    fun Long.toFormattedDateTime(format: String): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(Date(this))
    }

    fun Long.toTimerFormattedText(): String {
        if (this < 0) return "00:00"
        val duration = Duration.ofMillis(this)
        val hours = duration.toHours()
        return "${if (hours > 0) "$hours:" else ""}${this.toFormattedDateTime("mm:ss")}"
    }

    fun getTodayDate() = Date().time.toFormattedDateTime(API_DATE_FORMAT)

    fun getYesterdayDate() =
        Date(Date().time - DAY).time.toFormattedDateTime(API_DATE_FORMAT)
}