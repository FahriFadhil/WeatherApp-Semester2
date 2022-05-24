package com.fahri.weatherapp.data.networks

import com.fahri.weatherapp.BuildConfig.API_KEY
import com.fahri.weatherapp.data.ForecastResponse
import com.fahri.weatherapp.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET ("weather")
    fun weatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String? = API_KEY
    ) : Call<WeatherResponse>

    @GET ("forecast")
    fun forecastByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String? = API_KEY
    ) : Call<ForecastResponse>

    @GET ("weather")
    fun weatherByDefault(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String? = API_KEY
    ) : Call<WeatherResponse>

    @GET ("forecast")
    fun forecastByDefault(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String? = API_KEY
    ) : Call<ForecastResponse>

}