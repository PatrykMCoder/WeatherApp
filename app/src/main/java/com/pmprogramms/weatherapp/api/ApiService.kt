package com.pmprogramms.weatherapp.api

import com.pmprogramms.weatherapp.model.WeatherModel
import com.pmprogramms.weatherapp.model.WeatherWithListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getCurrentWeatherByCity(@Query("q") city: String, @Query("appid") appID: String, @Query("units") metrics: String) : Call<WeatherModel>

    @GET("forecast")
    fun getHourlyWeatherByCity(@Query("q") city: String, @Query("appid") appID: String, @Query("units") metrics: String, @Query("cnt") cnt: Int = 16) : Call<WeatherWithListModel>
}