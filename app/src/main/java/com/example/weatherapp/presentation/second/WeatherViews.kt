package com.example.weatherapp.presentation.second

import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

data class WeatherViews(
    val cityName: TextView,
    val lastUpdate: TextView,
    val currentTemp: TextView,
    val minCurrentTemp: TextView,
    val maxCurrentTemp: TextView,
    val rainchance: TextView,
    val feellike: TextView,
    val condition: TextView,
    val sunrise: TextView,
    val sunset: TextView,
    val precip: TextView,
    val humidity: TextView,
    val uv: TextView,
    val winddir: TextView,
    val windkph: TextView,
    val viskm: TextView,
    val pressure: TextView,
    val sun: ProgressBar,
    val rv_hour: RecyclerView,
    val sw: SwipeRefreshLayout,
    val secondF: ConstraintLayout,
    val rv_day: RecyclerView,
    val region: TextView,
    val country: TextView,
) {
    fun setLoadingState() {
        cityName.text = "..."
        lastUpdate.text = "..."
        currentTemp.text = "..."
        minCurrentTemp.text = "..."
        maxCurrentTemp.text = "..."
        rainchance.text = "..."
        feellike.text = "..."
        condition.text = "..."
        sunrise.text = "..."
        sunset.text = "..."
        precip.text = "..."
        humidity.text = "..."
        uv.text = "..."
        winddir.text = "..."
        windkph.text = "..."
        viskm.text = "..."
        pressure.text = "..."
        region.text = "..."
        country.text = "..."
    }

    fun setErrorState() {
        cityName.text = "Error"
        lastUpdate.text = "Error"
        currentTemp.text = "Error"
        minCurrentTemp.text = "Error"
        maxCurrentTemp.text = "Error"
        rainchance.text = "Error"
        feellike.text = "Error"
        condition.text = "Error"
        sunrise.text = "Error"
        sunset.text = "Error"
        precip.text = "Error"
        humidity.text = "Error"
        uv.text = "Error"
        winddir.text = "Error"
        windkph.text = "Error"
        viskm.text = "Error"
        pressure.text = "Error"
        region.text = "Error"
        country.text = "Error"
    }
}
