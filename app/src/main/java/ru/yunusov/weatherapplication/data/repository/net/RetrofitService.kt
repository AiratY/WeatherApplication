package ru.yunusov.weatherapplication.data.repository.net

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val API_URL = "https://api.openweathermap.org"

    private val retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)
}
