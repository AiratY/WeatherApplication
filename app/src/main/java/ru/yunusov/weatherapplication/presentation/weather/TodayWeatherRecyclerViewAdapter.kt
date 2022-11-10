package ru.yunusov.weatherapplication.presentation.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.yunusov.weatherapplication.R
import ru.yunusov.weatherapplication.model.WeatherItemDay
import ru.yunusov.weatherapplication.other.PicassoHelper

class TodayWeatherRecyclerViewAdapter :
    RecyclerView.Adapter<TodayWeatherRecyclerViewAdapter.ViewHolder>() {
    private var dataSet: List<WeatherItemDay> = emptyList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        private val popTextView: TextView = view.findViewById(R.id.popTextView)
        private val iconImageView: ImageView = view.findViewById(R.id.iconTodayImageView)
        private val tempItemTextView: TextView = view.findViewById(R.id.tempItemTextView)

        fun bind(weather: WeatherItemDay) {
            timeTextView.text = weather.time
            popTextView.text = weather.pop
            tempItemTextView.text = weather.temp
            PicassoHelper.setIconImageView(weather.srcIcon, iconImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_today, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSet(data: List<WeatherItemDay>) {
        dataSet = data
        notifyDataSetChanged()
    }
}
