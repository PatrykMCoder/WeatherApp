package com.pmprogramms.weatherapp.utils.helper

import android.util.Log
import com.pmprogramms.weatherapp.R
import java.sql.Time
import java.util.*

class BackgroundHelper {
    fun returnBackgroundID(sunset: Long): Int {
        val time = Time.from(Date().toInstant()).time
        return if (time >= sunset * 1000) {
            R.drawable.night_sky
        } else {
            R.drawable.day_sky
        }
    }
}