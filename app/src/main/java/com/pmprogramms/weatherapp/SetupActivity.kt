package com.pmprogramms.weatherapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.pmprogramms.weatherapp.databinding.ActivitySetupBinding
import com.pmprogramms.weatherapp.utils.Variables
import com.pmprogramms.weatherapp.utils.enums.LanguageEnum
import com.pmprogramms.weatherapp.utils.enums.UnitEnum
import com.pmprogramms.weatherapp.utils.json.JsonUtil

class SetupActivity : AppCompatActivity(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private lateinit var binding: ActivitySetupBinding
    private lateinit var unitEnum: UnitEnum
    private lateinit var arrayOfLanguagesValue: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.unitsGroup.setOnCheckedChangeListener(this)
        binding.save.setOnClickListener(this)
        binding.languageSpinner.adapter = setupArrayForSpinner()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.save -> {
                save()
            }
        }
    }

    override fun onCheckedChanged(radio: RadioGroup, value: Int) {
        when(radio.checkedRadioButtonId) {
            R.id.metric_radio -> {
                unitEnum = UnitEnum.METRIC
            }
            R.id.imperial_radio -> {
                unitEnum = UnitEnum.IMPERIAL
            }
        }
    }

    private fun setupArrayForSpinner() : ArrayAdapter<String> {
        arrayOfLanguagesValue = ArrayList()
        val arrayLanguagesName = ArrayList<String>()

        for (item in LanguageEnum.values().iterator()) {
            val firstChar = item.toString().toCharArray()[0].toString()
            val otherChars = item.toString().substring(1, item.toString().length).lowercase()
            val fullName = StringBuilder(firstChar).append(otherChars).toString()
            arrayOfLanguagesValue.add(item.lang)
            arrayLanguagesName.add(fullName)

        }
        return ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
        arrayLanguagesName)
    }

    private fun save() {
        val locationString = binding.location.text.toString().trim()
        val selectedLanguage = arrayOfLanguagesValue[binding.languageSpinner.selectedItemPosition]
        val listLocation = arrayListOf(locationString)
        val sharedPreferences = getSharedPreferences(Variables.LOCATION_SHARED_DATA_KEY, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Variables.LOCATION_SHARED_DATA_KEY_LOCATION, JsonUtil().generateJSONString(listLocation))
        editor.putString(Variables.LOCATION_SHARED_DATA_KEY_UNITS, unitEnum.value)
        editor.putString(Variables.LOCATION_SHARED_DATA_KEY_LANGUAGE, selectedLanguage)
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}