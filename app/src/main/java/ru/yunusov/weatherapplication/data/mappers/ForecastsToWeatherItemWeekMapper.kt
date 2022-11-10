package ru.yunusov.weatherapplication.data.mappers

import ru.yunusov.weatherapplication.data.model.Forecast
import ru.yunusov.weatherapplication.model.WeatherItemWeek
import ru.yunusov.weatherapplication.other.fromShortDate
import ru.yunusov.weatherapplication.other.getDataFromUnix
import ru.yunusov.weatherapplication.other.getShortDate
import java.time.LocalDate

class ForecastsToWeatherItemWeekMapper {
    /**
     * Создает список элементов погоды на ближающие 5 дней
     * @param listForecasts - Изначальный результат обращения к API
     * @return список WeatherItemWeek - элементы погоды на 5 дн.
     * */
    fun fromForecasts(listForecasts: List<Forecast>): List<WeatherItemWeek> {
        val nowDate = LocalDate.now()
        val resultWeek: MutableList<WeatherItemWeek> = mutableListOf()
        for (item in listForecasts) {
            val date = item.dt.getDataFromUnix().toLocalDate()

            if (date > nowDate) {
                val weatherItemWeek = resultWeek.find { it.date.fromShortDate() == date }
                val minTemp = item.main.tempMin.toInt()
                val maxTemp = item.main.tempMax.toInt()
                val icon = item.weather[0].icon

                if (weatherItemWeek == null) {
                    resultWeek.add(WeatherItemWeek(date.getShortDate(), minTemp, maxTemp, icon))
                } else {
                    if (minTemp < weatherItemWeek.minTemp) {
                        weatherItemWeek.minTemp = minTemp
                    }
                    if (maxTemp > weatherItemWeek.maxTemp) {
                        weatherItemWeek.maxTemp = maxTemp
                    }
                    if (icon > weatherItemWeek.srcIcon) {
                        weatherItemWeek.srcIcon = icon
                    }
                }
            }
        }
        return resultWeek
    }
}
