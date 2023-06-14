package com.melihcan.todoapp.extensions

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = dateFormat.format(calendar.time)
    return currentDate
}

fun getCurrentWeekOfYear(): Int {
    val cal = Calendar.getInstance()
    return cal.get(Calendar.WEEK_OF_YEAR)
}

fun getSelectedWeekOfYear(selectedDate: Long): Int {
    val cal = Calendar.getInstance()
    cal.timeInMillis = selectedDate
    val day = cal.get(Calendar.DAY_OF_WEEK)
    val weekOfYear = cal.get(Calendar.WEEK_OF_YEAR)
    if (day == Calendar.SUNDAY) {
        return weekOfYear - 1
    }
    return weekOfYear
}

fun getSelectedDayOfYear(selectedDate: Long): Int {
    val cal = Calendar.getInstance()
    cal.timeInMillis = selectedDate
    var day = cal.get(Calendar.DAY_OF_WEEK)
    if (day == Calendar.SUNDAY) {
        day = 6
    } else {
        day -= 2
    }
    return day
}

fun getSelectedDate(selectedDate: Long): String {
    val cal = Calendar.getInstance()
    cal.timeInMillis = selectedDate
    val dateFormat = SimpleDateFormat("MMM-dd")
    val selectedDate = dateFormat.format(cal.time)
    return selectedDate
}
fun getCurrentDayOfWeek(): Int {
    val cal = Calendar.getInstance()
    cal.firstDayOfWeek = Calendar.MONDAY
    var day = cal.get(Calendar.DAY_OF_WEEK)
    if (day == Calendar.SUNDAY) {
        day = 6
    } else {
        day -= 2
    }
    return day
}


fun getFirstDayOfWeek(week: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.set(Calendar.WEEK_OF_YEAR, week + 1)
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

    val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
    return dateFormat.format(calendar.time).toInt()
}

fun getCurrentMonth(): String {
    val cal = Calendar.getInstance()
    val format = SimpleDateFormat("MMM")
    return format.format(cal.get(Calendar.MONTH + 1))
}