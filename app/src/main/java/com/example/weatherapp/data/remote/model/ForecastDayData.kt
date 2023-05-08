package com.example.weatherapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastDayData(
    @SerializedName("date") val date: String,
    @SerializedName("astro") val astro: Astro,
    @SerializedName("hour") val hour: List<HourData>,
    @SerializedName("day") val day: ForecastDayDetailsData
):Parcelable
