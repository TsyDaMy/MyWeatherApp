package com.example.weatherapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentData(
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("temp_c") val tempCelsius: Double,
    @SerializedName("is_day") val is_day: Int,
    @SerializedName("condition") val condition: ConditionData,
    @SerializedName("feelslike_c") val feelsLikeCelsius: Double,
    @SerializedName("precip_mm") val precipMillimeters: Double,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("wind_kph") val windSpeed: Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("vis_km") val visibility: Double,
    @SerializedName("uv") val uvIndex: Double,
    @SerializedName("pressure_mb") val pressure: Double
):Parcelable
