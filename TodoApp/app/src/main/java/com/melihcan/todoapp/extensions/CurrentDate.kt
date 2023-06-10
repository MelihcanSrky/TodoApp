package com.melihcan.todoapp.extensions

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

data class PairDate(val date: String, val dayName: String)

@SuppressLint("NewApi")
fun CurrentDate() : PairDate {
    val cal = Calendar.getInstance()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val dayDate = SimpleDateFormat("EEEE")
    val dayName = dayDate.format(cal.time)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        PairDate(date = LocalDate.now().format(formatter), dayName = dayName)
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}