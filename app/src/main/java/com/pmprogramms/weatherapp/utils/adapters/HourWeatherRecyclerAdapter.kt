package com.pmprogramms.weatherapp.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmprogramms.weatherapp.R
import com.pmprogramms.weatherapp.model.DataList
import com.pmprogramms.weatherapp.utils.helper.GlideHelper
import java.text.SimpleDateFormat
import java.util.*

class HourWeatherRecyclerAdapter :
    RecyclerView.Adapter<HourWeatherRecyclerAdapter.ViewHolder>() {
    private val glideHelper = GlideHelper()
    private lateinit var weatherData: List<DataList>

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val temperatureTextView: TextView = view.findViewById(R.id.temperature)
        private val timeTextView: TextView = view.findViewById(R.id.time)
        private val icon: ImageView = view.findViewById(R.id.icon_image_view)

        fun bindData(weatherData: DataList) {
            temperatureTextView.text = weatherData.main.temp.toString()
            timeTextView.text = formatDate(weatherData.time * 1000)

            glideHelper.loadIcon(icon, weatherData.weather[0].icon)
        }

        private fun formatDate(time: Long): String {
            return SimpleDateFormat("dd-MM\nhh:mm").format(Date(time))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindData(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    fun setData(weatherData: List<DataList>) {
        this.weatherData = weatherData
    }
}