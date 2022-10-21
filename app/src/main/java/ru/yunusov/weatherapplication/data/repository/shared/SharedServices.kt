package ru.yunusov.weatherapplication.data.repository.shared

import android.content.Context
import ru.yunusov.weatherapplication.App
import ru.yunusov.weatherapplication.R
import ru.yunusov.weatherapplication.domain.SavedCities
import ru.yunusov.weatherapplication.domain.SavedMainCity

class SharedServices : SavedCities, SavedMainCity {

    private val sharedPreference = App.getAppContext()?.getSharedPreferences(
        PREFERENCE_KEY,
        Context.MODE_PRIVATE
    )
    private val savedList = mutableListOf<String>()

    init {
        val list = sharedPreference?.getStringSet(
            App.getAppContext()?.getString(R.string.list_cities),
            setOf()
        )
        list?.let { savedList.addAll(it) }
    }

    override fun loadList(): List<String> {
        return savedList
    }

    override fun saveNew(city: String) {
        sharedPreference?.edit()?.let {
            val setCities = savedList.toMutableSet().apply { add(city) }
            it.putStringSet(STRING_SET_KEY, setCities)
            it.apply()
        }
    }

    override fun removeCity(city: String) {
        savedList.remove(city)
    }

    override fun loadMainCity(): String? {
        return savedList.firstOrNull()
    }

    companion object {
        private val PREFERENCE_KEY = App.getAppContext()?.getString(R.string.preference_file_key)
        private val STRING_SET_KEY = App.getAppContext()?.getString(R.string.list_cities)
    }
}
