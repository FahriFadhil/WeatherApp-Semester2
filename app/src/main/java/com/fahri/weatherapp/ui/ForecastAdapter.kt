package com.fahri.weatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahri.weatherapp.BuildConfig
import com.fahri.weatherapp.data.ForecastResponse
import com.fahri.weatherapp.data.ListItem
import com.fahri.weatherapp.databinding.RowItemWeatherBinding
import com.fahri.weatherapp.utils.HelperFunctions.kelvinToCelcius
import com.fahri.weatherapp.utils.iconSizeLarge
import com.fahri.weatherapp.utils.iconSizeMedium
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.MyViewHolder> () {

    private var listWeather = ArrayList<ListItem>()

    class MyViewHolder (val binding: RowItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder (
        RowItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val data = listWeather[i]
        holder.binding.apply {
            tvMaxDegree.text = "Max: " + data.main?.tempMax?.let { kelvinToCelcius(it) }
            tvMinDegree.text = "Min: " + data.main?.tempMin?.let { kelvinToCelcius(it) }

            val dateArr = data.dtTxt?.take(10)?.split("-")?.toTypedArray()
            val timeArr = data.dtTxt?.takeLast(8)?.split(":")?.toTypedArray()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, Integer.parseInt(dateArr?.get(0) as String))
            calendar.set(Calendar.MONTH, Integer.parseInt(dateArr[1]) - 1)
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArr[2]))
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr?.get(0) as String))
            calendar.set(Calendar.MINUTE, 0)

            Glide.with(imgItemWeather)
                .load(BuildConfig.ICON_URL + data.weather?.get(0)?.icon + iconSizeLarge)
                .into(imgItemWeather)

            tvItemTime.text = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
                .format(calendar.time).toString()
            tvItemDate.text = SimpleDateFormat("h:MM a", Locale.getDefault())
                .format(calendar.time).toString()
        }
    }

    override fun getItemCount() = listWeather.size

    fun setdata(data: List<ListItem>?) {
        if (data == null) return
        listWeather.clear()
        listWeather.addAll(data)
    }

}