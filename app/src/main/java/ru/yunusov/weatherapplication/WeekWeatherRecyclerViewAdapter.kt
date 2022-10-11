package ru.yunusov.weatherapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.yunusov.weatherapplication.data.model.WeatherItemWeek
import java.lang.ref.WeakReference

class WeekWeatherRecyclerViewAdapter(context: Context) :
    RecyclerView.Adapter<WeekWeatherRecyclerViewAdapter.ViewHolder>() {
    private var dataSet: List<WeatherItemWeek> = emptyList()
    private val weakReferenceContext = WeakReference(context)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.findViewById(R.id.dayOfWeekTextView)
        val iconDayOfWeek: ImageView = view.findViewById(R.id.iconDayOfWeekImageView)
        val tempDayOfWeek: TextView = view.findViewById(R.id.tempOfWeekTextView)

        fun bind(weatherItemWeek: WeatherItemWeek) {
            dayTextView.text = weatherItemWeek.date.getShortDate()
            tempDayOfWeek.text = weakReferenceContext.get()?.getString(R.string.max_and_min_temp, weatherItemWeek.maxTemp,weatherItemWeek.minTemp)
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