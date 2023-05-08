package com.example.weatherapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastData(
    @SerializedName("forecastday") val forecastDays: List<ForecastDayData>
): Parcelable
