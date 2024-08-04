package com.pmprogramms.weatherapp.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pmprogramms.weatherapp.R
import com.pmprogramms.weatherapp.model.WeatherModel
import com.pmprogramms.weatherapp.utils.helper.GlideHelper

class LocationListWeatherRecyclerAdapter() : RecyclerView.Adapter<LocationListWeatherRecyclerAdapter.ViewHolder>(){
    private val glideHelper = GlideHelper()
    private lateinit var data: List<WeatherModel?>

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val cityTV = view.findViewById<TextView>(R.id.city)
        private val tempTV = view.findViewById<TextView>(R.id.temperature)
        private val cardView = view.findViewById<CardView>(R.id.card)
        private val expandView = view.findViewById<LinearLayout>(R.id.expand_view)
        private val windSpeed = view.findViewById<TextView>(R.id.wind_speed)
        private val pressure = view.findViewById<TextView>(R.id.pressure)
        private val humidity = view.findViewById<TextView>(R.id.humidity)
        private val icon = view.findViewById<ImageView>(R.id.image_icon)

        fun bind(model: WeatherModel?) {
            model?.let {
                var visible = false
                cityTV.text = model.city
                tempTV.text = "${model.main.temp.toInt()}°"
                windSpeed.text = "Wind speed: ${model.wind.speed} km/h"
                pressure.text = "Pressure: ${model.main.pressure.toInt()}"
                humidity.text = "Humidity: ${model.main.humidity.toInt()}"

                glideHelper.loadIcon(icon, model.weather[0].icon)
                cardView.setOnClickListener {
                    if (!visible) {
                        expandView.visibility = View.VISIBLE
                        visible = true
                    } else {
                        expandView.visibility = View.GONE
                        visible = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.count { it != null }
    }

    fun setData(data: List<WeatherModel?>) {
        this.data = data
    }
}