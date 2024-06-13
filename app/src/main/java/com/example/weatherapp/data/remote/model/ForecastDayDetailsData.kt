package com.example.weatherapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastDayDetailsData(
    @SerializedName("maxtemp_c") val maxTempCelsius: Double,
    @SerializedName("mintemp_c") val minTempCelsius: Double,
    @SerializedName("daily_chance_of_rain") val chanceOfRain: Double,
    @SerializedName("condition") val condition: ConditionData
): Parcelable
