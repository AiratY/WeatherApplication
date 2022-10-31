package ru.yunusov.weatherapplication.presentation.main

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import ru.yunusov.weatherapplication.data.repository.shared.SharedServices
import ru.yunusov.weatherapplication.domain.SavedMainCity
import ru.yunusov.weatherapplication.other.CITY_NAME

class MainViewModel : ViewModel() {

    private val savedMainCity: SavedMainCity = SharedServices()
    private val cityName: String? = savedMainCity.loadMainCity()

    /**
     * Возвращает объкт Bundle с аргументом cityName
     * @return Bundle
     * */
    fun getBundleArgument(): Bundle {
        return bundleOf(CITY_NAME to cityName)
    }
}
