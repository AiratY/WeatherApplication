package ru.yunusov.weatherapplication.other

import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.yunusov.weatherapplication.R

class PicassoHelper {

    companion object {
        private const val BASE_URL = "http://openweathermap.org/img/wn/"
        private const val END = "@2x.png"

        /**
         * Загружает изображение и устанавливает в ImageView
         * */
        fun setIconImageView(idIcon: String, imageView: ImageView) {
            Picasso.get()
                .load(BASE_URL + idIcon + END)
                .placeholder(R.mipmap.ic_sunny)
                .error(R.mipmap.ic_sunny)
                .fit()
                .into(imageView)
        }
    }
}
