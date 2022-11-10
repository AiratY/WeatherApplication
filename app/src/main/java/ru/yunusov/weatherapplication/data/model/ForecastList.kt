package ru.yunusov.weatherapplication.data.model

data class ForecastList(
    val id: Int,
    val cod: Long,
    var list: List<Forecast>,
    val city: City
)
