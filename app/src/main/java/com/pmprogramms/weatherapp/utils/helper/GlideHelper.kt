package com.pmprogramms.weatherapp.utils.helper

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideHelper {
    fun loadIcon(targetView: ImageView, iconName: String) {
        Glide.with(targetView).load("https://openweathermap.org/img/wn/${iconName}@2x.png").override(250).into(targetView)
    }
}