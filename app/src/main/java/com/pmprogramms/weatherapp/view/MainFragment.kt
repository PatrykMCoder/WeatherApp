package com.pmprogramms.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmprogramms.weatherapp.BuildConfig
import com.pmprogramms.weatherapp.R
import com.pmprogramms.weatherapp.databinding.FragmentMainBinding
import com.pmprogramms.weatherapp.model.DataList
import com.pmprogramms.weatherapp.model.WeatherModel
import com.pmprogramms.weatherapp.utils.Variables
import com.pmprogramms.weatherapp.utils.adapters.HourWeatherRecyclerAdapter
import com.pmprogramms.weatherapp.utils.helper.DayHelper
import com.pmprogramms.weatherapp.utils.helper.GlideHelper
import com.pmprogramms.weatherapp.utils.json.JsonUtil
import com.pmprogramms.weatherapp.viewmodel.WeatherViewModel
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class MainFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var unitsString: String
    private lateinit var languageString: String
    private lateinit var locationsString: List<String>
    private lateinit var hourWeatherRecyclerAdapter: HourWeatherRecyclerAdapter
    private lateinit var weatherData: ArrayList<DataList>

    private val jsonUtil = JsonUtil()
    private val glideHelper = GlideHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.bar.menuLocation.setOnClickListener(this)
        weatherData = ArrayList()
        hourWeatherRecyclerAdapter = HourWeatherRecyclerAdapter()
        hourWeatherRecyclerAdapter.setData(weatherData)

        binding.hourlyWeatherData.apply {
            setHasFixedSize(true)
            adapter =hourWeatherRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        readDataFromSharedPreference()
        return binding.root
    }

    private fun readDataFromSharedPreference() {
        val sharedPreferences = requireContext().getSharedPreferences(Variables.LOCATION_SHARED_DATA_KEY,
            AppCompatActivity.MODE_PRIVATE
        )
        locationsString = jsonUtil.readFromJSON(sharedPreferences.getString(Variables.LOCATION_SHARED_DATA_KEY_LOCATION, "")!!)
        unitsString = sharedPreferences.getString(Variables.LOCATION_SHARED_DATA_KEY_UNITS, "")!!
        languageString = sharedPreferences.getString(Variables.LOCATION_SHARED_DATA_KEY_LANGUAGE, "")!!

        loadData()
    }

    private fun loadData() {
        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        viewModel.getCurrentWeatherByCity(lifecycleScope, locationsString, BuildConfig.OPENWEATHERMAP_API_KEY, unitsString)
            .observe(
                viewLifecycleOwner
            ) { weatherData ->
                weatherData?.let {
                    weatherData[0]?.let { data -> updateUI(data) }
                }
            }

        viewModel.getHourlyWeatherByCity(locationsString[0], BuildConfig.OPENWEATHERMAP_API_KEY, unitsString)
            .observe(
                viewLifecycleOwner
            ) { result ->
                result.fold(
                    onSuccess = { data ->
                        data.list.forEach {
                            weatherData.add(it)
                            hourWeatherRecyclerAdapter.notifyItemInserted(weatherData.size - 1)
                        }
                    },
                    onFailure = {exception ->
                        val alertDialog = AlertDialog.Builder(requireContext()).apply {
                            setTitle("Error")
                            setMessage(exception.localizedMessage)
                            setPositiveButton("OK"
                            ) { _, _ ->
                                exitProcess(-1)
                            }
                        }

                        alertDialog.show()
                    }
                )
            }
    }

    private fun updateUI(model: WeatherModel) {
        val dayDetailsView = binding.dayDetails
        binding.bar.homeCity.text = model.city
        binding.temperature.text = resources.getString(R.string.temperature_value, model.main.temp.toInt())
        binding.shortDescription.text = model.weather[0].description
        binding.date.text = getToday()

        dayDetailsView.temperatureFeelsLike.text =
            resources.getString(R.string.feels_like_value, model.main.feelsLike.toInt())
        dayDetailsView.pressure.text =
            resources.getString(R.string.pressure_value, model.main.pressure.toInt())
        dayDetailsView.humidity.text =
            resources.getString(R.string.humidity_value, model.main.humidity.toInt())
        dayDetailsView.windSpeed.text =
            resources.getString(R.string.wind_speed_value, model.wind.speed.toInt())

        glideHelper.loadIcon(binding.iconImageView, model.weather[0].icon)
    }

    private fun getToday(): String {
        val calendar = Calendar.getInstance()
        val dateInfo = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        return DayHelper.formatDay(dateInfo)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.menu_location -> {
                findNavController().navigate(R.id.action_mainFragment_to_listWeatherFragment)
            }
        }
    }
}