package ru.yunusov.weatherapplication.data.model

import ru.yunusov.weatherapplication.App
import ru.yunusov.weatherapplication.R
import ru.yunusov.weatherapplication.other.convertToKM
import ru.yunusov.weatherapplication.other.convertToPercent
import ru.yunusov.weatherapplication.other.getWithPercent

data class Forecast(
    var dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,

) {
    /**
     * Возвращает описание прогноза погоды
     * */
    fun getDescription(): String {
        return this.weather[0].description.replaceFirstChar { it.uppercase() }
    }

    /**
     * Возвращает облачность в виде строки с % в конце
     * */
    fun getCloudsString(): String {
        return this.clouds.all.getWithPercent()
    }

    /**
     * Возвращает Видимость в виде строки с км в конце
     * */
    fun getVisibilityString(): String {
        return App.getAppContext()?.getString(R.string.km_end, this.visibility.convertToKM()) ?: ""
    }

    /**
     * Возвращает вероятность осадков в виде строки с % в конце
     * */
    fun getPopString(): String {
        return this.pop.convertToPercent().getWithPercent()
    }

    /**
     * Возвращает Ветер в виде строки с м/с в конце
     * */
    fun getWindString(): String {
        return App.getAppContext()?.getString(R.string.speed_wind, this.wind.speed) ?: ""
    }
}
