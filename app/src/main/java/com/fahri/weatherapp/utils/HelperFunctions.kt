package com.fahri.weatherapp.utils

import java.math.RoundingMode

object HelperFunctions {

    fun kelvinToCelcius(temp: Double): String {
        return "${(temp - 273.15).toBigDecimal().setScale(2, RoundingMode.FLOOR)}°C"
    }

}