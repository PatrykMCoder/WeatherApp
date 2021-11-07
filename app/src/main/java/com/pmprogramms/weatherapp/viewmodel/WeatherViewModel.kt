package com.pmprogramms.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.pmprogramms.weatherapp.model.WeatherModel
import com.pmprogramms.weatherapp.model.WeatherWithListModel
import com.pmprogramms.weatherapp.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository()


    fun getCurrentWeatherByCity(cities: List<String>, appID: String, units: String) = liveData<List<WeatherModel?>> {
        val weatherModels = cities.map { city ->
            GlobalScope.async { repository.getCurrentWeatherByCity(city, appID, units) }
        }
            .awaitAll()
        emit(weatherModels)
    }


    fun getHourlyWeatherByCity(city: String, appID: String, units: String) : LiveData<WeatherWithListModel> {
        return repository.getHourlyWeatherByCity(city, appID, units)
    }
}