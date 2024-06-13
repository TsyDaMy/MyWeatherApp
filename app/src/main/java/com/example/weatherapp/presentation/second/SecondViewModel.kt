package com.example.weatherapp.presentation.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.remote.model.WeatherData
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class SecondViewModel(private val weatherRepository: WeatherRepository): ViewModel() {
    private val _currentWeatherState = MutableLiveData<WeatherState>()
    val currentWeatherState: LiveData<WeatherState> = _currentWeatherState

    private val _forecastWeatherState = MutableLiveData<WeatherState>()
    val forecastWeatherState: LiveData<WeatherState> = _forecastWeatherState

    fun transformDate(date: String): String {
        val parts = date.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]

        val transformedDate = "$day.$month"

        return transformedDate
    }

    fun extractTimeFromDateTime(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateTime)
        return outputFormat.format(date)
    }

    fun getMillisFromTimeString(timeString: String): Long {
        val time = timeString.substringAfter(" ")
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = format.parse(time)
        return date?.time ?: 0L
    }

    fun convertTo24HourFormat(timeString: String): String {
        val format12Hour = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val format24Hour = SimpleDateFormat("HH:mm", Locale.getDefault())

        val date12Hour = format12Hour.parse(timeString)
        return format24Hour.format(date12Hour)
    }

    fun getMillisFrom24HourTime(timeString: String): Long {
        val format24Hour = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date24Hour = format24Hour.parse(timeString)
        return date24Hour?.time ?: 0L
    }

    private var city: String? = null
    private var region: String = "" // Змінна для зберігання регіону

    fun setCity(city: String?, region: String = "") {
        this.city = city
        this.region = region
        refreshWeather()
    }

    fun refreshWeather() {
        city?.let { cityName ->
            val location = if (region.isNotBlank()) "$cityName, $region" else cityName
            viewModelScope.launch {
                _currentWeatherState.value = WeatherState.Loading
                _forecastWeatherState.value = WeatherState.Loading
                try {
                    val (currentResponse, forecastResponse) = weatherRepository.getWeather(cityName = location)
                    handleCurrentResponse(currentResponse)
                    handleForecastResponse(forecastResponse)
                } catch (e: Exception) {
                    _currentWeatherState.value = WeatherState.Error(e.message ?: "Unknown error")
                    _forecastWeatherState.value = WeatherState.Error(e.message ?: "Unknown error")
                }

            }
        }
    }

    private fun handleCurrentResponse(response: Response<WeatherData>) {
        if (response.isSuccessful) {
            val weatherData = response.body()
            weatherData?.let {
                _currentWeatherState.value = WeatherState.Success(it)
            } ?: run {
                _currentWeatherState.value = WeatherState.Error("Data is null")
            }
        } else {
            _currentWeatherState.value = WeatherState.Error(response.message())
        }
    }

    private fun handleForecastResponse(response: Response<WeatherData>) {
        if (response.isSuccessful) {
            val weatherData = response.body()
            weatherData?.let {
                _forecastWeatherState.value = WeatherState.Success(it)
            } ?: run {
                _forecastWeatherState.value = WeatherState.Error("Data is null")
            }
        } else {
            _forecastWeatherState.value = WeatherState.Error(response.message())
        }
    }

    sealed class WeatherState {
        object Loading : WeatherState()
        data class Success(val weatherData: WeatherData) : WeatherState()
        data class Error(val message: String) : WeatherState()
    }
}