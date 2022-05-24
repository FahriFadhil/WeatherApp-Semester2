package com.fahri.weatherapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahri.weatherapp.data.ForecastResponse
import com.fahri.weatherapp.data.WeatherResponse
import com.fahri.weatherapp.data.networks.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val weatherByCity = MutableLiveData<WeatherResponse>()
    private val forecastByCity = MutableLiveData<ForecastResponse>()
    private val weatherByCurrentLocation = MutableLiveData<WeatherResponse>()
    private val forecastByCurrentLocation = MutableLiveData<ForecastResponse>()

    fun weatherByCity(city: String) {
        ApiConfig().getApiServices().weatherByCity(city).enqueue(object: Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                weatherByCity.postValue(response.body())
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("Failure Call Api", t.message.toString())
            }
        })
    }
    fun getWeatherByCity() : LiveData<WeatherResponse> = weatherByCity

    fun forecastByCity(city: String) {
        ApiConfig().getApiServices().forecastByCity(city).enqueue(object :
            Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                forecastByCity.postValue(response.body())
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.e("Failure Call Api", t.message.toString())
            }
        })
    }
    fun getForecastByCity() : LiveData<ForecastResponse> = forecastByCity

    fun weatherByCurrentLocation(lat: Double, lon: Double) {
        ApiConfig().getApiServices().weatherByDefault(lat, lon).enqueue(object :
            Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                weatherByCurrentLocation.postValue(response.body())
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getWeatherByCurrentLocation() : LiveData<WeatherResponse> = weatherByCurrentLocation

    fun forecastByCurrentLocation(lat: Double, lon: Double) {
        ApiConfig().getApiServices().forecastByDefault(lat, lon).enqueue(object :
            Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                forecastByCurrentLocation.postValue(response.body())
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun getForecastByCurrentLocation() : LiveData<ForecastResponse> = forecastByCurrentLocation
}