package com.pmprogramms.weatherapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmprogramms.weatherapp.utils.Variables

class LoadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        val intent = if (checkSavedLocation()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, SetupActivity::class.java)

        }
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun checkSavedLocation(): Boolean{
        val sharedPreferences = getSharedPreferences(Variables.LOCATION_SHARED_DATA_KEY, MODE_PRIVATE)
        val savedLocations = sharedPreferences.all

        return savedLocations.isNotEmpty()
    }
}