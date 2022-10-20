package ru.yunusov.weatherapplication.presentation.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.yunusov.weatherapplication.App
import ru.yunusov.weatherapplication.R
import ru.yunusov.weatherapplication.data.model.WeatherItemWeek
import ru.yunusov.weatherapplication.other.PicassoHelper
import ru.yunusov.weatherapplication.other.getShortDate

class WeekWeatherRecyclerViewAdapter :
    RecyclerView.Adapter<WeekWeatherRecyclerViewAdapter.ViewHolder>() {
    private var dataSet: List<WeatherItemWeek> = emptyList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dayTextView: TextView = view.findViewById(R.id.dayOfWeekTextView)
        private val iconDayOfWeek: ImageView = view.findViewById(R.id.iconDayOfWeekImageView)
        private val tempDayOfWeek: TextView = view.findViewById(R.id.tempOfWeekTextView)

        fun bind(weatherItemWeek: WeatherItemWeek) {
            dayTextView.text = weatherItemWeek.date.getShortDate()
            tempDayOfWeek.text = App.getAppContext()?.getString(
                R.string.max_and_min_temp,
                weatherItemWeek.maxTemp,
                weatherItemWeek.minTemp
            )
            PicassoHelper.setIconImageView(weatherItemWeek.iconId, iconDayOfWeek)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.week_weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSet(data: List<WeatherItemWeek>) {
        dataSet = data
        notifyDataSetChanged()
    }
}
