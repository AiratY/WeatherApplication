package ru.yunusov.weatherapplication.data.mappers

import ru.yunusov.weatherapplication.data.model.Forecast
import ru.yunusov.weatherapplication.model.WeatherItemDay
import ru.yunusov.weatherapplication.other.getDataFromUnix
import ru.yunusov.weatherapplication.other.getTime

class ForecastsToWeatherItemDayMapper {
    fun fromListForecasts(forecasts: List<Forecast>): List<WeatherItemDay> {
        val newList = mutableListOf<WeatherItemDay>()

        for (forecast in forecasts) {
            newList.add(
                WeatherItemDay(
                    time = forecast.dt.getDataFromUnix().getTime(),
                    pop = forecast.getPopString(),
                    srcIcon = forecast.weather[0].icon,
                    temp = forecast.main.getTempWithDegree()
                )
            )
        }
        return newList
    }
}
