package ru.yunusov.weatherapplication.data.model

import com.google.gson.annotations.SerializedName
import ru.yunusov.weatherapplication.App
import ru.yunusov.weatherapplication.R
import ru.yunusov.weatherapplication.other.getWitchDegree
import ru.yunusov.weatherapplication.other.getWithPercent

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
) {
    fun getTempWithDegree(): String {
        return this.temp.toInt().getWitchDegree()
    }

    fun getFeelsLikeWithDegree(): String {
        return App.getAppContext()?.getString(R.string.feels_temp, this.feelsLike.toInt()) ?: ""
    }

    /**
     * Возвращает Влажность в виде строки с % в конце
     * */
    fun getHumidityWithPercent(): String {
        return this.humidity.getWithPercent()
    }

    /**
     * Возвращает давление в виде строки с окончание мбар
     * */
    fun getPressureString(): String {
        return App.getAppContext()?.getString(R.string.pressure, this.pressure) ?: ""
    }
}