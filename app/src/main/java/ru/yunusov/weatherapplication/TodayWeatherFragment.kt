package ru.yunusov.weatherapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import ru.yunusov.weatherapplication.data.Forecast
import ru.yunusov.weatherapplication.data.ResultList
import ru.yunusov.weatherapplication.data.RetrofitService
import ru.yunusov.weatherapplication.data.model.WeatherItemWeek
import java.net.UnknownHostException
import java.time.LocalDate

class TodayWeatherFragment : Fragment() {

    private var tempTextView: TextView? = null
    private var feelsLikeTextView: TextView? = null
    private var weatherTextView: TextView? = null
    private var humidityTextView: TextView? = null
    private var pressureTextView: TextView? = null
    private var cloudsTextView: TextView? = null
    private var visibilityTextView: TextView? = null
    private var popTextView: TextView? = null
    private var windTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    private var mainGroup: Group? = null
    private var solveErrorBtn: Button? = null
    private var iconImageView: ImageView? = null

    private var todayWeatherRecyclerViewAdapter: TodayWeatherRecyclerViewAdapter? = null
    private var weekWeatherRecyclerViewAdapter: WeekWeatherRecyclerViewAdapter? = null

    private var cityName: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_today_wheather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cityName = it.getString(CITY_NAME)
        }

        tempTextView = view.findViewById(R.id.tempTextView)
        feelsLikeTextView = view.findViewById(R.id.feelsLikeTextView)
        weatherTextView = view.findViewById(R.id.weatherTextView)
        humidityTextView = view.findViewById(R.id.humidityTextView)
        pressureTextView = view.findViewById(R.id.pressureTextView)
        cloudsTextView = view.findViewById(R.id.cloudsTextView)
        visibilityTextView = view.findViewById(R.id.visibilityTextView)
        popTextView = view.findViewById(R.id.popTextView)
        windTextView = view.findViewById(R.id.windTextView)
        val nameCityTextView: TextView = view.findViewById(R.id.nameCityTextView)
        progressBar = view.findViewById(R.id.progressBar)
        mainGroup = view.findViewById(R.id.mainGroup)
        solveErrorBtn = view.findViewById(R.id.solveErrorButton)
        iconImageView = view.findViewById(R.id.iconImageView)
        val editImageView: ImageView = view.findViewById(R.id.editImageView)

        val todayRecyclerView: RecyclerView = view.findViewById(R.id.todayWeatherRecyclerView)
        todayWeatherRecyclerViewAdapter = TodayWeatherRecyclerViewAdapter(requireContext())
        todayRecyclerView.adapter = todayWeatherRecyclerViewAdapter

        val weekRecyclerView: RecyclerView = view.findViewById(R.id.weekWeatherRecyclerView)
        weekWeatherRecyclerViewAdapter = WeekWeatherRecyclerViewAdapter(requireContext())
        weekRecyclerView.adapter = weekWeatherRecyclerViewAdapter
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { dividerItemDecoration.setDrawable(it) }
        weekRecyclerView.addItemDecoration(dividerItemDecoration)

        nameCityTextView.text = cityName

        cityName?.let { getDataFromAPI(it) }

        editImageView.setOnClickListener {
            showScreenSelectCity()
        }
        solveErrorBtn?.setOnClickListener {
            when((it as Button).text) {
                CHANGE_CITY -> showScreenSelectCity()
                UPDATE -> cityName?.let { city -> getDataFromAPI(city) }
            }
            goneSolveErrorBtn()
            showProgressBar()
        }
    }

    /**
     * Показывает progressBar
     * */

    private fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    /**
     * Скрывает SolveErrorButton
     * */

    private fun goneSolveErrorBtn() {
        solveErrorBtn?.visibility = View.GONE
    }

    /**
     * Осуществляет переход на экран для выбора города
     * */

    private fun showScreenSelectCity() {
        setFragmentResult(REQUEST_SHOW_SELECT_CITY, bundleOf())
    }

    /**
     * Запрашивает данные о погоде в городе
     * @param city - город для поиска
     * */

    private fun getDataFromAPI(city: String) {
        RetrofitService.weatherService.getWeather(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resultList -> setData(resultList) },
                { e -> onGetWeatherFail(e) }
            )
    }

    /**
     * Скрывает progressbar и показывает все компоненты
     * */
    private fun goneProgressbarANDshowAll() {
        goneProgressbar()
        mainGroup?.visibility = View.VISIBLE
    }

    /**
     * Скрывает progressbar
     * */
    private fun goneProgressbar() {
        progressBar?.visibility = View.GONE
    }

    /**
     * Обробатывает исключения
     * @param throwable - исключение
     * */
    private fun onGetWeatherFail(throwable: Throwable) {
        goneProgressbar()
        when(throwable) {
            is HttpException -> {
                showMessage(NOT_FOUND)
                showSolveErrorBtn(CHANGE_CITY)
            }
            is UnknownHostException -> {
                showMessage(NO_CONNECTION)
                showSolveErrorBtn(UPDATE)
            }
            else -> {
                throwable.message?.let { showMessage(it) }
                showSolveErrorBtn(UPDATE)
            }
        }
    }

    /**
     * Показывает кнопку для решения проблемы и указывает текст
     * @param text - текст, кот. устанавливаеться кнопки
     * */
    private fun showSolveErrorBtn(text: String) {
        solveErrorBtn?.visibility = View.VISIBLE
        solveErrorBtn?.text = text
    }

    /**
     * Показывает сообщение на экран
     * @param message - сообщение
     * */
    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    /**
     * Отображает данные полученные от запроса к API
     * @param resultList - список результатов запроса
     * */
    private fun setData(resultList: ResultList) {
        val forecast = resultList.list[0]
        setDataOnTextView(forecast)
        iconImageView?.let { PicassoHelper.setIconImageView(forecast.weather[0].icon, it) }
        todayWeatherRecyclerViewAdapter?.setDataSet(resultList.list.subList(0, 8))
        weekWeatherRecyclerViewAdapter?.setDataSet(getListWeekWeather(resultList.list))
        goneProgressbarANDshowAll()
    }

    /**
     * Заполняет все TextViews на экране данными из forecast
     * @param forecast - прогноз погоды
     * */
    private fun setDataOnTextView(forecast: Forecast) {
        val main = forecast.main
        tempTextView?.text = main.temp.toInt().getWitchDegree(requireContext())
        feelsLikeTextView?.text = getString(R.string.feels_temp, main.feelsLike.toInt())
        weatherTextView?.text = forecast.weather[0].description.replaceFirstChar { it.uppercase() }
        humidityTextView?.text = main.humidity.getWithPercent(requireContext())
        pressureTextView?.text = getString(R.string.pressure, main.pressure)
        cloudsTextView?.text = forecast.clouds.all.getWithPercent(requireContext())
        visibilityTextView?.text = getString(R.string.km_end, forecast.visibility.convertToKM())
        popTextView?.text = forecast.pop.convertToPercent().getWithPercent(requireContext())
        windTextView?.text = getString(R.string.speed_wind, forecast.wind.speed)
    }

    /**
     * Создает список элементов погоды на ближающие 5 дней
     * @param beginList - Изначальный результат обращения к API
     * @return список WeatherItemWeek - элементы погоды на 5 дн.
     * */
    private fun getListWeekWeather(beginList: List<Forecast>): List<WeatherItemWeek> {
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

    companion object {
        private const val NO_CONNECTION = "Сеть недоступна. Проверьте подключение и повторите попытку"
        private const val NOT_FOUND = "Прогноз погоды в этом городе не найден"
        private const val CHANGE_CITY = "Выбрать другой город"
        private const val UPDATE = "Обновить"

        private const val CITY_NAME = "city_name"
        const val REQUEST_SHOW_SELECT_CITY = "show_select_city"

        fun newInstance(city: String): TodayWeatherFragment {
            return TodayWeatherFragment().apply {
                arguments = bundleOf(
                    CITY_NAME to city
                )
            }
        }
    }
}