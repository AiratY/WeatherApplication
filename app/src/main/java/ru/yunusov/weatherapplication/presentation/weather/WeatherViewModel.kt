package ru.yunusov.weatherapplication.presentation.weather

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yunusov.weatherapplication.domain.ForecastInteractor
import ru.yunusov.weatherapplication.domain.IForecastInteractor
import ru.yunusov.weatherapplication.model.Resource
import ru.yunusov.weatherapplication.model.WeatherForecast
import ru.yunusov.weatherapplication.other.CHANGE_CITY
import ru.yunusov.weatherapplication.other.Event
import ru.yunusov.weatherapplication.other.UPDATE

class WeatherViewModel(val cityName: String) : ViewModel() {

    private val _isSetMainCity = MutableLiveData<Boolean>()
    val isSetMainCity: LiveData<Boolean> get() = _isSetMainCity

    private val _isSelectCity = MutableLiveData<Event<Boolean>>()
    val isSelectCity: LiveData<Event<Boolean>> get() = _isSelectCity

    private val iForecastInteractor: IForecastInteractor by lazy {
        ForecastInteractor.newInstance(
            cityName
        )
    }
    var data: LiveData<Resource<WeatherForecast>>? = null

    init {
        if (cityName == "") {
            setMainCity()
        } else {
            data = iForecastInteractor.getForecast()
        }
    }

    /**
     * Поменять город для прогноза
     * */
    fun editCityName() {
        _isSelectCity.value = Event(true)
    }

    /**
     * SolveErrorBtn была нажата
     * */
    fun enterSolveErrorBtn(view: View) {
        (view as? Button)?.let {
            when (view.text) {
                CHANGE_CITY -> editCityName()
                UPDATE -> iForecastInteractor.refresh()
            }
        }
    }

    /**
     * Установить основной город для прогноза
     * */
    private fun setMainCity() {
        _isSetMainCity.value = true
    }
}
