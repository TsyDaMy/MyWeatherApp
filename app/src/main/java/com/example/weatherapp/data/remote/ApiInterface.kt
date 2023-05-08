package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") cityName: String,
        @Query("lang") lang: String = "uk"
    ): Response<WeatherData>

    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("key") apiKey: String,
        @Query("q") cityName: String,
        @Query("days") days: Int = 7,
        @Query("lang") lang: String = "uk"
    ): Response<WeatherData>
}