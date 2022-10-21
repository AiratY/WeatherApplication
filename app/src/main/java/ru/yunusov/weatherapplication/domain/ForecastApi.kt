package ru.yunusov.weatherapplication.domain

interface ForecastApi {
    /**
     * Загружает прогноз погоды
     * @param cityName - город для которого прогноз загружаеться
     * */
    fun loadForecast(cityName: String, forecastOutput: ForecastOutput)
}
