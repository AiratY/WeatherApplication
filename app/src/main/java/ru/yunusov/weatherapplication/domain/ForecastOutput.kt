package ru.yunusov.weatherapplication.domain

import ru.yunusov.weatherapplication.data.model.ForecastList

interface ForecastOutput {
    /**
     * Передает результат пргноза
     * */
    fun setData(result: ForecastList)

    /**
     * Передает ошибку
     * */
    fun setError(throwable: Throwable)
}
