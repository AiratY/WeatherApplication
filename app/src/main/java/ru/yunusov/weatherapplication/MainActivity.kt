package ru.yunusov.weatherapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shared =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val cityName = shared.getString(getString(R.string.main_city), null)

        if (savedInstanceState == null || !savedInstanceState.getBoolean(IS_FRAGMENT_CRATED,false)) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                val fragment = if (cityName == null) {
                    SelectCityFragment.newInstance(true)
                } else {
                    TodayWeatherFragment.newInstance(cityName)
                }
                add(R.id.fragment_container_view, fragment)
            }
        }

        supportFragmentManager.setFragmentResultListener(
            SelectCityFragment.REQUEST_SHOW_FORECAST,
            this
        ) { _, bundle ->
            val city = bundle.getString(SelectCityFragment.BUNDLE_CITY_NAME)
            city?.let { replaceFragment(TodayWeatherFragment.newInstance(it)) }
        }

        supportFragmentManager.setFragmentResultListener(
            TodayWeatherFragment.REQUEST_SHOW_SELECT_CITY,
            this
        ) { _, _ ->
            replaceFragment(SelectCityFragment.newInstance(false))
        }
    }

    /**
     * Меняет фрагмент
     * @param fragment - новый фрагмент
     * */

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_FRAGMENT_CRATED, true)
    }

    companion object {
        private const val IS_FRAGMENT_CRATED = "is_fragment_created"
    }
}