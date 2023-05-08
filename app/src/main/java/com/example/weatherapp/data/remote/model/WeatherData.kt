package com.example.weatherapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherData(
    @SerializedName("location") val location: LocationData,
    @SerializedName("current") val current: CurrentData,
    @SerializedName("forecast") val forecast: ForecastData
): Parcelable
