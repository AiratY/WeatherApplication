package ru.yunusov.weatherapplication.presentation.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yunusov.weatherapplication.domain.SavedMainCity
import ru.yunusov.weatherapplication.data.repository.shared.SharedServices
import ru.yunusov.weatherapplication.presentation.selectcity.SelectCityFragment
import ru.yunusov.weatherapplication.presentation.weather.WeatherFragment

class MainViewModel : ViewModel() {

    private val mutableFragment = MutableLiveData<Fragment>()
    val fragment: LiveData<Fragment> get() = mutableFragment

    private val savedMainCity: SavedMainCity = SharedServices()

    init {
        val cityName = savedMainCity.loadMainCity()
        if (cityName == null) {
            showSelectCity()
        } else {
            showWeather(cityName)
        }
    }

    /**
     * Показывает WeatherFragment
     * @param city - название города которое выбрал пользователь
     * */
    fun showWeather(city: String) {
        mutableFragment.value = WeatherFragment.newInstance(city)
    }

    /**
     * Показывает фрагмент для выбора города
     * */
    fun showSelectCity() {
        mutableFragment.value = SelectCityFragment.newInstance()
    }
}
