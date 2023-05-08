package com.example.weatherapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConditionData(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String
): Parcelable
