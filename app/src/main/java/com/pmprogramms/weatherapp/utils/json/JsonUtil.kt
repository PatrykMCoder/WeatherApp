package com.pmprogramms.weatherapp.utils.json

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonUtil {
    fun generateJSONString(list: ArrayList<String>) : String {
        val gson = Gson()
        return  gson.toJson(list)
    }

    fun readFromJSON(json: String): ArrayList<String> {
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<ArrayList<String>>(){}.type)
    }
}