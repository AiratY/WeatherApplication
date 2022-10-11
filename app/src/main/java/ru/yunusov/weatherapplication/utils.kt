package ru.yunusov.weatherapplication

import android.content.Context
import java.lang.ref.WeakReference
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

const val UTC_ZONE = 4
const val NANO_OF_SECOND = 0
const val KM = 1000
const val PERCENTS = 100

fun LocalDateTime.getTime(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun Long.getDataFromUnix(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(this, NANO_OF_SECOND, ZoneOffset.ofHours(UTC_ZONE))
}

fun LocalDate.getShortDate(): String{
    return this.format(DateTimeFormatter.ofPattern("E, d, LLL"))
}

fun Double.convertToPercent(): Int {
    return (this* PERCENTS).toInt()
}
/**
 * Переводит метры в километры
 * */
fun Int.convertToKM(): Int{
    return this/KM
}

fun Int.getWithPercent(context: Context): String {
    val weakReferenceContext = WeakReference(context)
    return weakReferenceContext.get()?.getString(R.string.percent, this) ?: ""
}

fun Int.getWitchDegree(context: Context): String {
    val weakReferenceContext = WeakReference(context)
    return weakReferenceContext.get()?.getString(R.string.temp, this) ?: ""
}

