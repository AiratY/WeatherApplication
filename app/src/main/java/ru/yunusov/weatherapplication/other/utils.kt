package ru.yunusov.weatherapplication.other

import ru.yunusov.weatherapplication.App
import ru.yunusov.weatherapplication.R
import java.lang.ref.WeakReference
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val NANO_OF_SECOND = 0
const val KM = 1000
const val PERCENTS = 100

/**
 * Возращает время от текущей даты в виде строки
 * */
fun LocalDateTime.getTime(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

/**
 * Преобразует UNIX в LocalDataTime
 * */
fun Long.getDataFromUnix(): LocalDateTime {
    val zoned = ZonedDateTime.now()
    return LocalDateTime.ofEpochSecond(this, NANO_OF_SECOND, zoned.offset)
}

/**
 * Возращает строку содержащию дату в коротком виде
 * */
fun LocalDate.getShortDate(): String {
    return this.format(DateTimeFormatter.ofPattern("E, d, LLL"))
}

/**
 * Число изменяет в проценты
 * */
fun Double.convertToPercent(): Int {
    return (this * PERCENTS).toInt()
}

/**
 * Переводит метры в километры
 * */
fun Int.convertToKM(): Int {
    return this / KM
}

/**
 * Возращает строку с числом и процентами
 * */
fun Int.getWithPercent(): String {
    return App.getAppContext()?.getString(R.string.percent, this) ?: ""
}

/**
 * Возвращает строку с числом и градусами цельсия
 * */
fun Int.getWitchDegree(): String {
    val weakReferenceContext = WeakReference(App.getAppContext())
    return weakReferenceContext.get()?.getString(R.string.temp, this) ?: ""
}
