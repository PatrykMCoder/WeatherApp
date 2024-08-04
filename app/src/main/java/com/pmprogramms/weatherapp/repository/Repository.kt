package com.pmprogramms.weatherapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pmprogramms.weatherapp.api.ApiService
import com.pmprogramms.weatherapp.model.WeatherModel
import com.pmprogramms.weatherapp.model.WeatherWithListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    private val hourlyWeatherData: MutableLiveData<Result<WeatherWithListModel>> = MutableLiveData<Result<WeatherWithListModel>>()

    private val apiService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun getCurrentWeatherByCity(city: String, appID: String, units: String ) : WeatherModel? {
        return runCatching { apiService.getCurrentWeatherByCity(city, appID, units).await() }
            .getOrNull()
    }

    fun getHourlyWeatherByCity(city: String, appID: String, units: String) : LiveData<Result<WeatherWithListModel>> {
        apiService.getHourlyWeatherByCity(city, appID, units).enqueue(object : Callback<WeatherWithListModel> {
            override fun onResponse(
                call: Call<WeatherWithListModel>,
                response: Response<WeatherWithListModel>
            ) {
                if (response.isSuccessful) {
                    hourlyWeatherData.postValue(Result.success(response.body()!!))
                } else {
                    response.message().let { err ->
                        hourlyWeatherData.postValue(Result.failure(Throwable(err.toString())))
                    }
                }
            }

            override fun onFailure(call: Call<WeatherWithListModel>, t: Throwable) {
                hourlyWeatherData.postValue(Result.failure(t))
            }
        })
        return hourlyWeatherData
    }
}