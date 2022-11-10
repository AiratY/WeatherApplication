package ru.yunusov.weatherapplication.data.repository

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

class ForecastRepository(
    private val weatherApi: WeatherApi,
    private val forecastDao: ForecastDao,
    private val city: String
) {
    val result = MediatorLiveData<Resource<WeatherForecast>>()
    private val data = forecastDao.getForecast(city)

    init {
        startLoading()
        result.addSource(data) { weatherForecast ->
            result.removeSource(data)
            if (weatherForecast != null) {
                result.value = Resource.success(weatherForecast)
            }
            refresh()
        }
    }

    /**
     * Обновляет данные
     * */
    fun refresh() {
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
        result.addSource(error) { msg ->
            result.removeSource(error)
            result.value = Resource.error(msg, data.value)
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
                result.addSource(data) {
                    result.value = Resource.success(it)
                }
            }
    }
    /**
     * Обозначает начало загрузки данных
     * */
    private fun startLoading() {
        result.value = Resource.loading(data.value)
    }

    companion object {
        /**
         * Возвращает экземпляр ForecastRepository
         * @return ForecastRepository
         * */
        fun newInstance(city: String): ForecastRepository {
            return ForecastRepository(RetrofitService.weatherApi, RoomService.getDB(), city)
        }
    }
}
