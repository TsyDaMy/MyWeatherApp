package com.example.weatherapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Astro(
    @SerializedName("sunrise") val sunrise: String,
    @SerializedName("sunset") val sunset: String
): Parcelable
