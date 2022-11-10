package ru.yunusov.weatherapplication.model

data class WeatherItemWeek(
    val date: String,
    var minTemp: Int,
    var maxTemp: Int,
    var srcIcon: String,
)
