package com.pmprogramms.weatherapp.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmprogramms.weatherapp.BuildConfig
import com.pmprogramms.weatherapp.databinding.FragmentListWeatherBinding
import com.pmprogramms.weatherapp.model.WeatherModel
import com.pmprogramms.weatherapp.utils.Variables
import com.pmprogramms.weatherapp.utils.adapters.LocationListWeatherRecyclerAdapter
import com.pmprogramms.weatherapp.utils.callbacks.DialogCallBack
import com.pmprogramms.weatherapp.utils.json.JsonUtil
import com.pmprogramms.weatherapp.viewmodel.WeatherViewModel
import java.util.Collections


class ListWeatherFragment : Fragment(), DialogCallBack {
    private lateinit var binding: FragmentListWeatherBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var unit: String
    private lateinit var weatherData: ArrayList<WeatherModel>
    private lateinit var weatherRecyclerAdapter: LocationListWeatherRecyclerAdapter
    private val jsonUtil = JsonUtil()

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            Collections.swap(weatherData, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            weatherRecyclerAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            updateOrderLocationsData()
            return true;
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

    }
    private val itemTouchHelper = ItemTouchHelper(simpleCallback);

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListWeatherBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        weatherData = ArrayList()
        weatherRecyclerAdapter = LocationListWeatherRecyclerAdapter()
        weatherRecyclerAdapter.setData(weatherData)

        binding.locations.apply {
            setHasFixedSize(true)
            adapter = weatherRecyclerAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        itemTouchHelper.attachToRecyclerView(binding.locations)

        binding.bar.addLocation.setOnClickListener {
            AddLocationDialog().show(this.childFragmentManager, "AddLocation")
        }

        getData()
        return binding.root
    }


    private fun getLocations(): ArrayList<String> {
        val sharedPreferences =
            requireContext().getSharedPreferences(Variables.LOCATION_SHARED_DATA_KEY, MODE_PRIVATE)

        unit = sharedPreferences.getString(Variables.LOCATION_SHARED_DATA_KEY_UNITS, "")!!

        return jsonUtil.readFromJSON(
            sharedPreferences.getString(
                Variables.LOCATION_SHARED_DATA_KEY_LOCATION,
                ""
            )!!
        )
    }

    private fun getData() {
        val cities = getLocations()
        viewModel.getCurrentWeatherByCity(lifecycleScope, cities, BuildConfig.OPENWEATHERMAP_API_KEY, unit).observe(viewLifecycleOwner) {
            for (weather in it) {
                weather?.let {
                    weatherData.add(weather)
                    weatherRecyclerAdapter.notifyItemInserted(weatherData.size - 1)
                }
            }
        }
    }

    override fun saveLocations(location: String) {
        val sharedPreferences =
            requireContext().getSharedPreferences(Variables.LOCATION_SHARED_DATA_KEY, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val list = getLocations()
        list.add(location)

        val json = jsonUtil.generateJSONString(list)
        editor.putString(Variables.LOCATION_SHARED_DATA_KEY_LOCATION, json)
        editor.apply()
    }

    override fun refreshLocationsData() {
        val size = weatherData.size
        weatherData.removeAll(weatherData.toSet())
        weatherRecyclerAdapter.notifyItemRangeRemoved(0, size)
        getData()
    }

    private fun updateOrderLocationsData() {
        val sharedPreferences =
            requireContext().getSharedPreferences(Variables.LOCATION_SHARED_DATA_KEY, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val locations = weatherData.map { it.city }
        val json = jsonUtil.generateJSONString(locations)
        editor.putString(Variables.LOCATION_SHARED_DATA_KEY_LOCATION, json)
        editor.apply()
    }
}