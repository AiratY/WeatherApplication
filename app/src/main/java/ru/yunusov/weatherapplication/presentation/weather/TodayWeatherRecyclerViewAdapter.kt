package ru.yunusov.weatherapplication.presentation.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.yunusov.weatherapplication.R
import ru.yunusov.weatherapplication.data.model.Forecast
import ru.yunusov.weatherapplication.other.PicassoHelper
import ru.yunusov.weatherapplication.other.convertToPercent
import ru.yunusov.weatherapplication.other.getDataFromUnix
import ru.yunusov.weatherapplication.other.getTime

class TodayWeatherRecyclerViewAdapter :
    RecyclerView.Adapter<TodayWeatherRecyclerViewAdapter.ViewHolder>() {
    private var dataSet: List<Forecast> = emptyList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        private val popTextView: TextView = view.findViewById(R.id.popTextView)
        private val iconImageView: ImageView = view.findViewById(R.id.iconTodayImageView)
        private val tempItemTextView: TextView = view.findViewById(R.id.tempItemTextView)

        fun bind(forecast: Forecast) {
            val date = forecast.dt.getDataFromUnix()
            timeTextView.text = date.getTime()
            val pop = forecast.pop.convertToPercent()
            if (pop > 50) {
                popTextView.text = forecast.getPopString()
            }
            tempItemTextView.text = forecast.main.getTempWithDegree()
            PicassoHelper.setIconImageView(forecast.weather[0].icon, iconImageView)
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
    fun setDataSet(data: List<Forecast>) {
        dataSet = data
        notifyDataSetChanged()
    }
}
