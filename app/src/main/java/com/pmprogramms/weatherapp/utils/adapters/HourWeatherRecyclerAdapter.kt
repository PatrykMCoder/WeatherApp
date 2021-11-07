package com.pmprogramms.weatherapp.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmprogramms.weatherapp.R
import com.pmprogramms.weatherapp.model.DataList
import com.pmprogramms.weatherapp.model.WeatherWithListModel
import java.text.SimpleDateFormat
import java.util.*

class HourWeatherRecyclerAdapter(private val weatherData: WeatherWithListModel) :
    RecyclerView.Adapter<HourWeatherRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val temperatureTextView: TextView = view.findViewById(R.id.temperature)
        private val timeTextView: TextView = view.findViewById(R.id.time)

        fun bindData(weatherData: DataList) {
            temperatureTextView.text = "${weatherData.main.temp.toInt()}°"
            timeTextView.text = formatDate(weatherData.time * 1000)
        }

        private fun formatDate(time: Long): String {
            return SimpleDateFormat("dd-MM\nhh:mm").format(Date(time))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindData(weatherData.list[position])
    }

    override fun getItemCount(): Int {
        return weatherData.list.size
    }
}