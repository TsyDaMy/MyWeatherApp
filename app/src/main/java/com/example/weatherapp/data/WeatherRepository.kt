package com.example.weatherapp.data

import com.example.weatherapp.data.remote.ApiFactory
import com.example.weatherapp.data.remote.model.WeatherData
import retrofit2.Response

object WeatherRepository {

    private val api = ApiFactory.weatherApi
    private val API_KEY = "04b9e735fc0544eb98f211327230305"

    suspend fun getWeather(apiKey: String = API_KEY, cityName: String): Pair<Response<WeatherData>, Response<WeatherData>> {
        val currentWeather = ApiFactory.weatherApi.getCurrentWeather(apiKey, cityName)
        val forecastWeather = ApiFactory.weatherApi.getForecastWeather(apiKey, cityName)
        return Pair(currentWeather, forecastWeather)
    }

}