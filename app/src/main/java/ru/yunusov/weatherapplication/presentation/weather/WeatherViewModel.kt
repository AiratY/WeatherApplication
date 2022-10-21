package ru.yunusov.weatherapplication.presentation.weather

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.HttpException
import ru.yunusov.weatherapplication.data.model.Forecast
import ru.yunusov.weatherapplication.data.model.ForecastList
import ru.yunusov.weatherapplication.data.model.WeatherItemWeek
import ru.yunusov.weatherapplication.domain.ForecastApi
import ru.yunusov.weatherapplication.domain.ForecastInteractor
import ru.yunusov.weatherapplication.domain.ForecastOutput
import ru.yunusov.weatherapplication.other.getDataFromUnix
import java.lang.ref.WeakReference
import java.net.UnknownHostException
import java.time.LocalDate

class WeatherViewModel(callback: SelectCityCallback) : ViewModel(), ForecastOutput {

    private val callbackWR = WeakReference(callback)

    private val _mainIcon = MutableLiveData("")
    val mainIcon: LiveData<String> get() = _mainIcon

    private var mutableThrowableMessage = MutableLiveData<String>()
    val throwableMessage: LiveData<String> get() = mutableThrowableMessage

    private val _stateProgressBar = MutableLiveData<Int>()
    val stateProgressBar: LiveData<Int> get() = _stateProgressBar

    private val _stateSolveErrorBtn = MutableLiveData(View.GONE)
    val stateSolveErrorBtn: LiveData<Int> get() = _stateSolveErrorBtn

    private val _textSolveBtn = MutableLiveData<String>()
    val textSolveBtn: LiveData<String> get() = _textSolveBtn

    private val _stateMainGroup = MutableLiveData(View.GONE)
    val stateMainGroup: LiveData<Int> get() = _stateMainGroup

    private val _currentForecast = MutableLiveData<Forecast>()
    val currentForecast: LiveData<Forecast> get() = _currentForecast

    val todayWeatherRecyclerViewAdapter = TodayWeatherRecyclerViewAdapter()
    val weekWeatherRecyclerViewAdapter = WeekWeatherRecyclerViewAdapter()

    val cityName = callbackWR.get()?.getCityName() ?: ""

    private val forecastApi: ForecastApi = ForecastInteractor()

    init {
        forecastApi.loadForecast(cityName, this)
    }

    /**
     * Поменять город для прогноза
     * */
    fun editCityName() {
        callbackWR.get()?.selectNewCity()
    }

    /**
     * SolveErrorBtn была нажата
     * */
    fun enterSolveErrorBtn() {
        when (textSolveBtn.value) {
            CHANGE_CITY -> editCityName()
            UPDATE -> forecastApi.loadForecast(cityName, this)
        }
        mutableThrowableMessage = MutableLiveData<String>()
        goneSolveErrorBtn()
        showProgressBar()
    }

    /**
     * Устанавливает новое собщение ошибки
     * @param message - сообщение
     * */
    private fun setMessageThrowable(message: String) {
        mutableThrowableMessage.value = message
    }

    /**
     * Изменяет состояние progressbar на Скрытый
     * */
    private fun goneProgressbar() {
        _stateProgressBar.value = View.GONE
    }

    /**
     * Показывает кнопку для решения проблемы и указывает текст
     * @param text - текст, кот. устанавливаеться кнопки
     * */
    private fun showSolveErrorBtn(text: String) {
        _stateSolveErrorBtn.value = View.VISIBLE
        _textSolveBtn.value = text
    }

    /**
     * Отображает данные полученные от запроса к API
     * @param result - список результатов запроса
     * */
    override fun setData(result: ForecastList) {
        _currentForecast.value = result.list[0]
        _mainIcon.value = currentForecast.value?.weather?.get(0)?.icon

        todayWeatherRecyclerViewAdapter.setDataSet(result.list.subList(0, 8))
        weekWeatherRecyclerViewAdapter.setDataSet(getListWeekWeather(result))

        goneProgressbarANDshowAll()
    }

    override fun setError(throwable: Throwable) {
        goneProgressbar()

        when (throwable) {
            is HttpException -> {
                setMessageThrowable(NOT_FOUND)
                showSolveErrorBtn(CHANGE_CITY)
            }
            is UnknownHostException -> {
                setMessageThrowable(NO_CONNECTION)
                showSolveErrorBtn(UPDATE)
            }
            else -> {
                throwable.message?.let { setMessageThrowable(it) }
                showSolveErrorBtn(UPDATE)
            }
        }
    }

    /**
     * Скрывает progressbar и показывает все компоненты
     * */
    private fun goneProgressbarANDshowAll() {
        goneProgressbar()
        _stateMainGroup.value = View.VISIBLE
    }

    /**
     * Создает список элементов погоды на ближающие 5 дней
     * @param forecastList - Изначальный результат обращения к API
     * @return список WeatherItemWeek - элементы погоды на 5 дн.
     * */
    private fun getListWeekWeather(forecastList: ForecastList): List<WeatherItemWeek> {
        val beginList: List<Forecast> = forecastList.list
        val nowDate = LocalDate.now()
        val resultWeek: MutableList<WeatherItemWeek> = mutableListOf()
        for (item in beginList) {
            val date = item.dt.getDataFromUnix().toLocalDate()

            if (date > nowDate) {
                val weatherItemWeek = resultWeek.find { it.date == date }
                val minTemp = item.main.tempMin.toInt()
                val maxTemp = item.main.tempMax.toInt()
                val icon = item.weather[0].icon

                if (weatherItemWeek == null) {
                    resultWeek.add(WeatherItemWeek(date, minTemp, maxTemp, icon))
                } else {
                    if (minTemp < weatherItemWeek.minTemp) {
                        weatherItemWeek.minTemp = minTemp
                    }
                    if (maxTemp > weatherItemWeek.maxTemp) {
                        weatherItemWeek.maxTemp = maxTemp
                    }
                    if (icon > weatherItemWeek.iconId) {
                        weatherItemWeek.iconId = icon
                    }
                }
            }
        }
        return resultWeek
    }

    /**
     * Показывает progressBar
     * */

    private fun showProgressBar() {
        _stateProgressBar.value = View.VISIBLE
    }

    /**
     * Скрывает SolveErrorButton
     */

    private fun goneSolveErrorBtn() {
        _stateSolveErrorBtn.value = View.GONE
    }

    companion object {
        private const val NO_CONNECTION =
            "Сеть недоступна. Проверьте подключение и повторите попытку"
        private const val NOT_FOUND = "Прогноз погоды в этом городе не найден"
        private const val CHANGE_CITY = "Выбрать другой город"
        private const val UPDATE = "Обновить"
    }
}
