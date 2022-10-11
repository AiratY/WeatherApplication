package ru.yunusov.weatherapplication.selectcity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.yunusov.weatherapplication.R
import java.lang.ref.WeakReference

class ResultSearchCityRecyclerViewAdapter(callback: CityCallback) :
    RecyclerView.Adapter<ResultSearchCityRecyclerViewAdapter.ViewHolder>() {
    private var dataSet: List<String> = emptyList()
    private val callbackWR = WeakReference(callback)
    private var isMayDelete = false

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cityTextView: TextView = view.findViewById(R.id.cityTextView)
        private val deleteImageView: ImageView = view.findViewById(R.id.deleteImageView)
        private var currentCity: String? = null

        init {
            view.setOnClickListener {
                currentCity?.let { city -> callbackWR.get()?.setCity(city.trim()) }
            }
            deleteImageView.setOnClickListener {
                currentCity?.let { city -> callbackWR.get()?.deleteCity(city.trim()) }
            }
        }

        fun bind(city: String) {
            currentCity = city
            cityTextView.text = city.trim()
            if (isMayDelete) {
                deleteImageView.visibility = View.VISIBLE
            } else {
                deleteImageView.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.result_search_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    /**
     * Передает данные адаптеру и указывает можно ли их удалять
     * @param data - данные
     * @param isDelete - указатель на удаление, если True то можно
     * */
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<String>, isDelete: Boolean = false) {
        dataSet = data
        isMayDelete = isDelete
        notifyDataSetChanged()
    }
}
