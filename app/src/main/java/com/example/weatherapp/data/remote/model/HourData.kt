package com.example.weatherapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HourData(
    @SerializedName("time") val time: String,
    @SerializedName("temp_c") val temp_c: Double,
    @SerializedName("condition") val condition: ConditionData,
    //@SerializedName("chance_of_rain") val chance_of_rain: Int,
    //@SerializedName("chance_of_snow") val chance_of_snow: Int,
): Parcelable
