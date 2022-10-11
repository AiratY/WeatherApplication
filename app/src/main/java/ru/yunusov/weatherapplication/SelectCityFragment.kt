package ru.yunusov.weatherapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jakewharton.rxbinding.widget.RxTextView
import java.io.InputStreamReader

class SelectCityFragment : Fragment(), CityCallback {

    private var isMainCity = true
    private var sharedPreference: SharedPreferences? = null
    private val listCities: MutableList<String> by lazy { getListSaveCities() }
    private var adapter: ResultSearchCityRecyclerViewAdapter? = null
    private var listCityFromBundle: ArrayList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            listCityFromBundle = it.getStringArrayList(LIST_CITIES)
        }

        arguments?.let {
            isMainCity = it.getBoolean(IS_MAIN_CITY, true)
        }

        sharedPreference = requireContext().getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )

        val cityEditText: EditText = view.findViewById(R.id.cityEditText)
        val resultSearchRecyclerView: RecyclerView =
            view.findViewById(R.id.resultSearchCityRecyclerView)

        adapter = ResultSearchCityRecyclerViewAdapter(this)
        resultSearchRecyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { dividerItemDecoration.setDrawable(it) }
        resultSearchRecyclerView.addItemDecoration(dividerItemDecoration)

        val assets = requireContext().assets.open("cities.json")
        val input = InputStreamReader(assets)
        val list = Gson().fromJson(input, Array<String>::class.java)

        RxTextView.textChanges(cityEditText).subscribe { city ->
            if (city.isNotEmpty()) {
                val searchCity = city.trim().toString().lowercase()
                val searchList: List<String> =
                    list.filter { it.lowercase().trim().contains(searchCity) }
                adapter?.setData(searchList)
            } else {
                if (!isMainCity) {
                    setSaveList()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(LIST_CITIES, ArrayList(listCities))
    }

    /**
     * Возвращает сохраненный список городов
     * */
    private fun getListSaveCities(): MutableList<String> {
        val list = if (listCityFromBundle == null) {
            sharedPreference?.getStringSet(getString(R.string.list_cities), setOf())
        } else {
            listCityFromBundle
        }
        return list?.toMutableList() ?: mutableListOf()
    }

    /**
     * Сохраняет название основного города
     * @param city - название города
     * */
    private fun saveMainCityName(city: String) {
        sharedPreference?.edit()?.let {
            it.putString(getString(R.string.main_city), city)
            it.putStringSet(getString(R.string.list_cities), setOf(city))
            it.apply()
        }
    }

    override fun setCity(city: String) {
        if (isMainCity) {
            saveMainCityName(city)
        } else {
            saveCity(city)
        }
        switchToForecast(city)
    }

    override fun deleteCity(city: String) {
        listCities.remove(city)
        setSaveList()
    }

    /**
     * Передает сохраненный список городов в адаптер
     * */
    private fun setSaveList() {
        adapter?.setData(listCities, true)
    }

    /**
     * Сохраняет город в список городов
     * @param city - название города
     * */
    private fun saveCity(city: String) {
        sharedPreference?.edit()?.let {
            val setCities = listCities.toMutableSet().apply { add(city) }
            it.putStringSet(getString(R.string.list_cities), setCities)
            it.apply()
        }
    }

    /**
     * Осуществляет переход к фрагменту для просмотра погоды
     * */
    private fun switchToForecast(city: String) {
        setFragmentResult(REQUEST_SHOW_FORECAST, bundleOf(BUNDLE_CITY_NAME to city))
    }

    companion object {
        private const val LIST_CITIES = "list_cities"
        private const val IS_MAIN_CITY = "is_main_city"
        const val REQUEST_SHOW_FORECAST = "show_forecast"
        const val BUNDLE_CITY_NAME = "city_name"

        /**
         * Возвращает экземпляр SelectCityFragment с переданными аргументами
         * @param isMainCity - флаг, Если True, то задаем основной город
         * */
        fun newInstance(isMainCity: Boolean) =
            SelectCityFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_MAIN_CITY, isMainCity)
                }
            }
    }
}