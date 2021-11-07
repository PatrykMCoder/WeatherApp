package com.pmprogramms.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    val weather: List<Weather>,
    val main: Main,
    val visibility: Double,
    val wind: Wind,
    val sys: Sys,
    @SerializedName("name")
    val city: String
)

data class Wind(
   val speed: Double,
)

data class Sys(
    val sunrise: Long,
    val sunset: Long
)
