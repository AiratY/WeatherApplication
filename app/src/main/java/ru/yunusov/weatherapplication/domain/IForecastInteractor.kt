package ru.yunusov.weatherapplication.domain

import androidx.lifecycle.LiveData
import ru.yunusov.weatherapplication.model.Resource
import ru.yunusov.weatherapplication.model.WeatherForecast

interface IForecastInteractor {
    /**
     * Обновляет данные
     * */
    fun refresh()

    /**
     * Возвращает прогноз погоды
     * @return LiveData<Resource<WeatherForecast>> - прогноз
     * */
    fun getForecast(): LiveData<Resource<WeatherForecast>>
}
