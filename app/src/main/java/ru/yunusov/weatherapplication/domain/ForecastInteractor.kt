package ru.yunusov.weatherapplication.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import ru.yunusov.weatherapplication.data.mappers.ForecastListToWeatherForecastMapper
import ru.yunusov.weatherapplication.data.repository.net.RetrofitService
import ru.yunusov.weatherapplication.data.repository.net.WeatherApi
import ru.yunusov.weatherapplication.data.repository.room.ForecastDao
import ru.yunusov.weatherapplication.data.repository.room.RoomService
import ru.yunusov.weatherapplication.model.Resource
import ru.yunusov.weatherapplication.model.WeatherForecast
import ru.yunusov.weatherapplication.other.NOT_FOUND
import ru.yunusov.weatherapplication.other.NO_CONNECTION
import java.net.UnknownHostException

class ForecastInteractor(
    private val weatherApi: WeatherApi,
    private val forecastDao: ForecastDao,
    private val city: String
) : IForecastInteractor {
    val forecast = MediatorLiveData<Resource<WeatherForecast>>()
    private val data = forecastDao.getForecast(city)

    init {
        startLoading()
        forecast.addSource(data) { weatherForecast ->
            forecast.removeSource(data)
            if (weatherForecast != null) {
                forecast.value = Resource.success(weatherForecast)
            }
            refresh()
        }
    }

    override fun getForecast(): LiveData<Resource<WeatherForecast>> {
        return forecast
    }

    /**
     * Обновляет данные
     * */
    override fun refresh() {
        val mapper = ForecastListToWeatherForecastMapper()
        startLoading()
        weatherApi.getWeather(city)
            .subscribeOn(Schedulers.io())
            .map { result -> mapper.fromForecastList(result) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resultList -> saveData(resultList) },
                { e -> setError(e) }
            )
    }

    /**
     * Обрабатывает ошибки
     * @param throwable - error
     * */
    private fun setError(throwable: Throwable) {
        var message = ""
        when (throwable) {
            is HttpException -> {
                message = NOT_FOUND
            }
            is UnknownHostException -> {
                message = NO_CONNECTION
            }
            else -> {
                throwable.message?.let { message = it }
            }
        }
        val error = MutableLiveData(message)
        forecast.addSource(error) { msg ->
            forecast.removeSource(error)
            forecast.value = Resource.error(msg, data.value)
        }
    }

    /**
     * Сохраняет значение
     * @param weather - данные
     * */
    private fun saveData(weather: WeatherForecast) {
        forecastDao.insertForecast(weather)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                forecast.addSource(data) {
                    forecast.value = Resource.success(it)
                }
            }
    }

    /**
     * Обозначает начало загрузки данных
     * */
    private fun startLoading() {
        forecast.value = Resource.loading(data.value)
    }

    companion object {
        /**
         * Возвращает экземпляр ForecastInteractor
         * @return ForecastRepository
         * */
        fun newInstance(city: String): ForecastInteractor {
            return ForecastInteractor(RetrofitService.weatherApi, RoomService.getDB(), city)
        }
    }
}
