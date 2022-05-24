package com.fahri.weatherapp.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @field:SerializedName("name")
    val name : String? = null,

    @field:SerializedName("weather")
    val weather : List<Weathers>? = null,

    @field:SerializedName("main")
    val main : Main? = null

)

data class Weathers(
    @field:SerializedName("id")
    val id : Int? = null,

    @field:SerializedName("main")
    val main : String? = null,

    @field:SerializedName("description")
    val description : String? = null,

    @field:SerializedName("icon")
    val icon : String? = null,
)

