package ru.yunusov.weatherapplication.data.repository.room

import androidx.room.TypeConverter
import ru.yunusov.weatherapplication.model.WeatherItemDay
import ru.yunusov.weatherapplication.model.WeatherItemWeek

class WeatherForecastConverters {

    @TypeConverter
    fun weatherDayToString(list: List<WeatherItemDay>): String {
        var result = ""
        for (item in list) {
            result += "${item.time}$DIV_FIELD${item.pop}$DIV_FIELD${item.srcIcon}$DIV_FIELD${item.temp}$DIV_ITEM"
        }
        return result
    }

    @TypeConverter
    fun stringToWeatherDay(value: String): List<WeatherItemDay> {
        val resultList = mutableListOf<WeatherItemDay>()
        val listStr = value.split(DIV_ITEM)
        for (item in listStr) {
            if (item == "") {
                break
            }
            val listItem = item.split(DIV_FIELD)
            val weatherItem = WeatherItemDay(listItem[0], listItem[1], listItem[2], listItem[3])
            resultList.add(weatherItem)
        }
        return resultList
    }

    @TypeConverter
    fun forecastDayToString(list: List<WeatherItemWeek>): String {
        var result = ""
        for (item in list) {
            result += "${item.date}$DIV_FIELD${item.minTemp}$DIV_FIELD${item.maxTemp}$DIV_FIELD${item.srcIcon}$DIV_ITEM"
        }
        return result
    }

    @TypeConverter
    fun stringToForecastDay(value: String): List<WeatherItemWeek> {
        val resultList = mutableListOf<WeatherItemWeek>()
        val listStr = value.split(DIV_ITEM)
        for (item in listStr) {
            if (item == "") {
                break
            }
            val listItem = item.split(DIV_FIELD)
            resultList.add(WeatherItemWeek(listItem[0], listItem[1].toInt(), listItem[2].toInt(), listItem[3]))
        }
        return resultList
    }

    companion object {
        private const val DIV_FIELD = ";"
        private const val DIV_ITEM = "/"
    }
}
