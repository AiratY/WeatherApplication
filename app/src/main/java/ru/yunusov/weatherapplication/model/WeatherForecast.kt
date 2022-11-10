package ru.yunusov.weatherapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherForecast(
    @PrimaryKey
    val cityName: String,
    val tempNow: String,
    val feelTempNow: String,
    val description: String,
    val iconSrc: String,
    val humidity: String,
    val pressure: String,
    val clouds: String,
    val visibility: String,
    val pop: String,
    val wind: String,
    val listWeatherDay: List<WeatherItemDay>,
    val listWeatherWeek: List<WeatherItemWeek>,
)
