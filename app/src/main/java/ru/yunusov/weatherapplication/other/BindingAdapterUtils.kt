package ru.yunusov.weatherapplication.other

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun setLoadImage(view: ImageView, src: String) {
    PicassoHelper.setIconImageView(src, view)
}
