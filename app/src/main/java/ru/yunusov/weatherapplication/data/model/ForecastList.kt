package ru.yunusov.weatherapplication.data.model

data class ForecastList(
    val cod: Long,
    val list: List<Forecast>
)
