package ru.yunusov.weatherapplication.other

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun setLoadImage(view: ImageView, src: String?) {
    src?.let { PicassoHelper.setIconImageView(src, view) }
}
