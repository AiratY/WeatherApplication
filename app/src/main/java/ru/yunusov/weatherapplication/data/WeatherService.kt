package ru.yunusov.weatherapplication.data

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yunusov.weatherapplication.data.model.ResultList

private const val API_KEY = "00c2f128f0403212d87221590ffa985e"
private const val DEFAULT_LANG = "RU"
private const val UNITS_OF_MEASUREMENT = "metric"

interface WeatherService {
    @GET("/data/2.5/forecast?appid=$API_KEY&lang=$DEFAULT_LANG&units=$UNITS_OF_MEASUREMENT")
    fun getWeather(@Query("q") cityName: String): Observable<ResultList>
}
