package ru.yunusov.weatherapplication.presentation.selectcity

interface ViewForecast {
    /**
     * Показывает прогноз погоды
     * @param city - город для прогноза
     * */
    fun showForecast(city: String)
}
