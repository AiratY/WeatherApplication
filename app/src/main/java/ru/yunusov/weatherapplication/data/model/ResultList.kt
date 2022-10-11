package ru.yunusov.weatherapplication.data.model

data class ResultList(
    val cod: Long,
    val list: List<Forecast>
)