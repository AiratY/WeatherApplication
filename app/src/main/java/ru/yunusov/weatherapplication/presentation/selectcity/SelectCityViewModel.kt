package ru.yunusov.weatherapplication.presentation.selectcity

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yunusov.weatherapplication.data.repository.gson.GsonService
import ru.yunusov.weatherapplication.data.repository.gson.ListAllCity
import ru.yunusov.weatherapplication.data.repository.shared.SavedCities
import ru.yunusov.weatherapplication.data.repository.shared.SharedServices
import ru.yunusov.weatherapplication.other.CITY_NAME

class SelectCityViewModel : ViewModel(), CityCallback {

    private var isMainCity = true

    var adapter = ResultSearchCityRecyclerViewAdapter(this)

    private val listAllCities: ListAllCity = GsonService()
    private val listDefaultCities = listAllCities.loadList()

    private val savedCities: SavedCities = SharedServices()
    private val listSavedCities = savedCities.loadList()

    private val _showForecastForCity: MutableLiveData<Bundle> = MutableLiveData()
    val showForecastForCity: LiveData<Bundle> get() = _showForecastForCity

    init {
        setSaveList()
        isMainCity = listSavedCities.isEmpty()
    }

    override fun setCity(city: String) {
        savedCities.saveNew(city)
        val bundleCity = bundleOf(CITY_NAME to city)
        _showForecastForCity.value = bundleCity
    }

    override fun deleteCity(city: String) {
        savedCities.removeCity(city)
        setSaveList()
    }

    /**
     * Изменение текста
     * */
    fun cityChange(city: CharSequence, start: Int, before: Int, count: Int) {
        if (city.isNotEmpty()) {
            val searchCity = city.trim().toString().lowercase()
            val searchList: List<String> =
                listDefaultCities.filter { it.lowercase().trim().contains(searchCity) }
            adapter.setData(searchList)
        } else {
            if (!isMainCity) {
                setSaveList()
            }
        }
    }

    /**
     * Передает сохраненный список городов в адаптер
     * */
    private fun setSaveList() {
        adapter.setData(listSavedCities, true)
    }
}
