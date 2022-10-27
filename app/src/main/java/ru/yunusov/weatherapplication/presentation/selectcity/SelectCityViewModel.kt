package ru.yunusov.weatherapplication.presentation.selectcity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yunusov.weatherapplication.data.repository.gson.GsonService
import ru.yunusov.weatherapplication.data.repository.shared.SharedServices
import ru.yunusov.weatherapplication.domain.ListAllCity
import ru.yunusov.weatherapplication.domain.SavedCities

class SelectCityViewModel : ViewModel(), CityCallback {

    private var isMainCity = true

    var adapter = ResultSearchCityRecyclerViewAdapter(this)

    private val listAllCities: ListAllCity = GsonService()
    private val listDefaultCities = listAllCities.loadList()

    private val savedCities: SavedCities = SharedServices()
    private val listSavedCities = savedCities.loadList()

    private val _showForecastForCity: MutableLiveData<String> = MutableLiveData()
    val showForecastForCity: LiveData<String> get() = _showForecastForCity

    init {
        setSaveList()
        isMainCity = listSavedCities.isEmpty()
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

    override fun setCity(city: String) {
        savedCities.saveNew(city)
        _showForecastForCity.value = city
    }

    override fun deleteCity(city: String) {
        savedCities.removeCity(city)
        setSaveList()
    }

    /**
     * Передает сохраненный список городов в адаптер
     * */
    private fun setSaveList() {
        adapter.setData(listSavedCities, true)
    }
}
