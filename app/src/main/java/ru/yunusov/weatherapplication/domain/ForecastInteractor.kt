package ru.yunusov.weatherapplication.domain

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.yunusov.weatherapplication.data.repository.net.RetrofitService

class ForecastInteractor : IForecastInteractor {

    override fun loadForecast(cityName: String, forecastOutput: ForecastOutput) {
        RetrofitService.weatherApi.getWeather(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resultList -> forecastOutput.setData(resultList) },
                { e -> forecastOutput.setError(e) }
            )
    }
}
