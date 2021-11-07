package com.pmprogramms.weatherapp.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmprogramms.weatherapp.R
import com.pmprogramms.weatherapp.databinding.FragmentListWeatherBinding
import com.pmprogramms.weatherapp.model.WeatherModel
import com.pmprogramms.weatherapp.utils.Variables
import com.pmprogramms.weatherapp.utils.adapters.LocationListWeatherRecyclerAdapter
import com.pmprogramms.weatherapp.utils.callbacks.DialogCallBack
import com.pmprogramms.weatherapp.utils.json.JsonUtil
import com.pmprogramms.weatherapp.viewmodel.WeatherViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ListWeatherFragment : Fragment(), DialogCallBack {
    private lateinit var binding: FragmentListWeatherBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var unit: String
    private lateinit var resultList: ArrayList<WeatherModel>
    private lateinit var appID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListWeatherBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        resultList = ArrayList()
        appID = resources.getString(R.string.appID)
        binding.bar.addLocation.setOnClickListener {
            AddLocationDialog().show(this.childFragmentManager, "AddLocation")

        }

        getData()
        return binding.root
    }

    private fun setUpRecyclerData(adapterRecycler: LocationListWeatherRecyclerAdapter) {
        binding.locations.apply {
            setHasFixedSize(true)
            adapter = adapterRecycler
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getLocations(): ArrayList<String> {
        val sharedPreferences =
            requireContext().getSharedPreferences(Variables.LOCATION_SHARED_DATA_KEY, MODE_PRIVATE)

        unit = sharedPreferences.getString(Variables.LOCATION_SHARED_DATA_KEY_UNITS, "")!!

        return JsonUtil().readFromJSON(
            sharedPreferences.getString(
                Variables.LOCATION_SHARED_DATA_KEY_LOCATION,
                ""
            )!!
        )
    }

    private fun getData() {
        val cities = getLocations()
        viewModel.getCurrentWeatherByCity(cities, appID, unit).observe(viewLifecycleOwner, {
            val adapterRecycler = LocationListWeatherRecyclerAdapter(it)
            setUpRecyclerData(adapterRecycler)
        })
    }

    override fun saveLocations(location: String) {
        val sharedPreferences =
            requireContext().getSharedPreferences(Variables.LOCATION_SHARED_DATA_KEY, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val list = getLocations()
        list.add(location)

        val json = JsonUtil().generateJSONString(list)
        editor.putString(Variables.LOCATION_SHARED_DATA_KEY_LOCATION, json)
        editor.apply()
    }

    override fun refreshLocationsData() {
       getData()
    }
}