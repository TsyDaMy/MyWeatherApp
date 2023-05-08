package com.example.weatherapp.presentation.first

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {

    private val _selectedCity = MutableLiveData<String>()
    val selectedCity: LiveData<String>
        get() = _selectedCity

    fun setSelectedCity(city: String) {
        _selectedCity.value = city
    }

    fun checkCityName(city: String, geocoder: Geocoder) {
        val pattern = """^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$""".toRegex()
        if (!pattern.matches(city)) {
            throw IllegalArgumentException("Будь ласка, введіть дійсну назву міста")
        }
        val addresses = geocoder.getFromLocationName(city, 1)
        if (addresses.isNullOrEmpty()) {
            throw IllegalArgumentException("Не вдалося знайти координати за назвою міста")
        }
    }
}
