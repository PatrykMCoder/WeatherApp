package com.pmprogramms.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherWithListModel(
    val list: List<DataList>
)

data class DataList(
    val main: Main,
    @SerializedName("dt")
    val time: Long,
    val weather: List<Weather>
)