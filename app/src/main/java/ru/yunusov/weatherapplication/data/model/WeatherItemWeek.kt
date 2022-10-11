package ru.yunusov.weatherapplication.data.model

import java.time.LocalDate

data class WeatherItemWeek(
    val date: LocalDate,
    var minTemp: Int,
    var maxTemp: Int,
    var iconId: String = ""
)
