package com.pmprogramms.weatherapp.utils.helper

import java.lang.StringBuilder

class DayHelper {

    companion object {
        fun formatDay(dateInfo: String): String {
            val pieceOfDateInfo = dateInfo.split(",")
            val dayName = pieceOfDateInfo[0].substring(0, 3)
            val month = pieceOfDateInfo[1].trim().split(" ")
            val monthName = month[0].substring(0, 3)
            val monthDayNumber = month[1]
            return StringBuilder("$dayName ").append("$monthDayNumber ").append(monthName).toString()
        }
    }
}