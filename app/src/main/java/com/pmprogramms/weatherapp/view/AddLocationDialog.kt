package com.pmprogramms.weatherapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText

import androidx.fragment.app.DialogFragment
import com.pmprogramms.weatherapp.R

class AddLocationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = View.inflate(context, R.layout.add_location_dialog, null)
        val tv = view.findViewById<EditText>(R.id.city_edit_text)
        builder.setView(view)
        builder.setPositiveButton(
            "Ok"
        ) { dialogInterface, _ ->
            dialogInterface.dismiss()
            run {
                (parentFragment as ListWeatherFragment).saveLocations(tv.text.toString().trim())
                (parentFragment as ListWeatherFragment).refreshLocationsData()
            }
        }
        return builder.create()
    }
}