package ru.yunusov.weatherapplication.data.mappers

import ru.yunusov.weatherapplication.data.model.ForecastList
import ru.yunusov.weatherapplication.model.WeatherForecast

class ForecastListToWeatherForecastMapper {
    fun fromForecastList(forecastList: ForecastList): WeatherForecast {
        val mapperToListToday = ForecastsToWeatherItemDayMapper()
        val mapperToWeatherWeek = ForecastsToWeatherItemWeekMapper()
        val currentForecast = forecastList.list[0]

        return WeatherForecast(
            cityName = forecastList.city.name,
            tempNow = currentForecast.main.getTempWithDegree(),
            feelTempNow = currentForecast.main.getFeelsLikeWithDegree(),
            description = currentForecast.getDescription(),
            iconSrc = currentForecast.weather[0].icon,
            humidity = currentForecast.main.getHumidityWithPercent(),
            pressure = currentForecast.main.getPressureString(),
            clouds = currentForecast.getCloudsString(),
            visibility = currentForecast.getVisibilityString(),
            pop = currentForecast.getPopString(),
            wind = currentForecast.getWindString(),
            listWeatherDay = mapperToListToday.fromListForecasts(forecastList.list.subList(0, 8)),
            listWeatherWeek = mapperToWeatherWeek.fromForecasts(forecastList.list),
        )
    }
}
