package com.pablosanchezegido.petcity.utils

import java.text.SimpleDateFormat
import java.util.*

private val DATE_PATTERN = "dd-MMM-yyyy"

fun getCurrentYear(): Int =
        Calendar.getInstance().get(Calendar.YEAR)

fun getCurrentMonth(): Int =
        Calendar.getInstance().get(Calendar.MONTH)

fun getCurrentDay(): Int =
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

fun getDateFormatted(year: Int, month: Int, day: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, day)

    val formatter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

    return formatter.format(calendar.time)
}

fun getDateFormatted(format: String): String {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat(format, Locale.getDefault())

    return formatter.format(calendar.time)
}

fun getDateTimestamp(dateString: String): Long {
    val formatter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
    val date = formatter.parse(dateString)

    return date.time
}

fun getDateTimestamp(year: Int, month: Int, day: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, day)

    return calendar.timeInMillis
}

fun getDateFromTimestamp(timestamp: Long, format: String? = null): String {
    val formatter = SimpleDateFormat(format ?: DATE_PATTERN, Locale.getDefault())

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    return formatter.format(calendar.time)
}
