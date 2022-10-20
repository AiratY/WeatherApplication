package ru.yunusov.weatherapplication.domain

import ru.yunusov.weatherapplication.presentation.weather.ForecastOutput

interface ForecastApi {
    /**
     * Загружает прогноз погоды
     * @param cityName - город для которого прогноз загружаеться
     * */
    fun loadForecast(cityName: String, forecastOutput: ForecastOutput)
}
