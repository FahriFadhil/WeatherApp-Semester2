package com.fahri.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fahri.weatherapp.BuildConfig
import com.fahri.weatherapp.data.ForecastResponse
import com.fahri.weatherapp.data.WeatherResponse
import com.fahri.weatherapp.databinding.ActivityMainBinding
import com.fahri.weatherapp.utils.HelperFunctions.kelvinToCelcius
import com.fahri.weatherapp.utils.LOCATION_PERMISSION_REQ_CODE
import com.fahri.weatherapp.utils.iconSizeLarge
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var _viewModel: MainViewModel? = null
    private val viewModel get() = _viewModel as MainViewModel

    private var _forecastAdapter: ForecastAdapter? = null
    private val forecastAdapter get() = _forecastAdapter as ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setAppAsFullScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        searchCity()
        getWeatherByCity()
        getCurrentLocationWeather()
    }

    private fun getWeatherByCity() {
        viewModel.getWeatherByCity().observe(this) {
            setupView(it, null)
        }

        _forecastAdapter = ForecastAdapter()
        viewModel.getForecastByCity().observe(this) {
            setupView(null, it)
        }
    }

    private fun getCurrentLocationWeather() {
        val fusedLocationProviderClient : FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding

                ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), LOCATION_PERMISSION_REQ_CODE)

            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            try {
                val lat = it.latitude
                val lon = it.longitude
//                viewModel.weatherByCurrentLocation(lat, lon)
//                viewModel.forecastByCurrentLocation(lat, lon)
            } catch (e: Throwable) {
                Log.i("MainActivity", "Last Location: $it", )
                Log.e("MainActivity", "Couldn't Get Current Location", )
            }
        }.addOnFailureListener {
            Log.e("MainActivity", "FusedLocationError: Failed Getting Current Location", )
        }

        viewModel.weatherByCurrentLocation(-6.23976000, 106.72741000)
        viewModel.getWeatherByCurrentLocation().observe(this) {
            setupView(it, null)
        }

        viewModel.forecastByCurrentLocation( -6.23976000, 106.72741000)
        viewModel.getForecastByCurrentLocation().observe(this) {
            setupView(null, it)
        }
    }

    private fun setAppAsFullScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetController?.isAppearanceLightStatusBars = true
    }

    private fun searchCity() {
        binding.edtSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.weatherByCity(it)
                        viewModel.forecastByCity(it)
                    }
                    try {
                        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
                    } catch (e : Throwable) {
                        Log.e("MainActivity", "onQueryTextSubmit: HideSoftWindow $e", )
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            }
        )
    }

    private fun setupView(weather: WeatherResponse?, forecast: ForecastResponse?) {
        weather?.let {
            binding.apply {
                tvCity.text = it.name
                tvDegree.text = it.main?.temp?.let { it1 -> kelvinToCelcius(it1) }

                Glide.with(applicationContext)
                    .load(BuildConfig.ICON_URL + it.weather?.get(0)?.icon + iconSizeLarge)
                    .into(imgIcWeather)

                rvWeatherForecast.apply {
                    forecastAdapter.setdata(forecast?.list)
                    layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                    adapter = forecastAdapter
                }
            }
        }
    }

}