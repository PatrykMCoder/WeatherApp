package com.pmprogramms.weatherapp.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pmprogramms.weatherapp.R
import com.pmprogramms.weatherapp.model.WeatherModel
import org.w3c.dom.Text

class LocationListWeatherRecyclerAdapter(private val data: List<WeatherModel?>) : RecyclerView.Adapter<LocationListWeatherRecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val cityTV = view.findViewById<TextView>(R.id.city)
        private val tempTV = view.findViewById<TextView>(R.id.temperature)
        private val cardView = view.findViewById<CardView>(R.id.card)
        private val expandView = view.findViewById<LinearLayout>(R.id.expand_view)
        private val windSpeed = view.findViewById<TextView>(R.id.wind_speed)
        private val pressure = view.findViewById<TextView>(R.id.pressure)
        private val humidity = view.findViewById<TextView>(R.id.humidity)

        fun bind(model: WeatherModel?) {
            if (model != null) {
                var visible = false
                cityTV.text = model.city
                tempTV.text = "${model.main.temp.toInt()}°"
                windSpeed.text = model.wind.speed.toString()
                pressure.text = model.main.pressure.toInt().toString()
                humidity.text = model.main.humidity.toInt().toString()

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
        return data.size
    }
}