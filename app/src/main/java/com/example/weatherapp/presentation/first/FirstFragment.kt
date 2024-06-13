package com.example.weatherapp.presentation.first

import android.graphics.drawable.AnimationDrawable
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFirstBinding
import java.io.IOException
import java.util.Locale

class FirstFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding
    lateinit var viewModel: FirstViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[FirstViewModel::class.java]
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Glide.with(binding.bg.context)
            .asGif()
            .load(R.drawable.storm)
            .into(binding.bg)

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val editTextCity = binding.cityEditText
        val editTextRegion = binding.regionEditText

        binding.confirmButton.setOnClickListener {
            val city = editTextCity.text.toString().trim()
            val region = editTextRegion.text.toString().trim()

            if (city.isNotBlank()) {
                try {
                    viewModel.checkCityName(city, geocoder)
                    viewModel.setSelectedCity(city)
                    val action = FirstFragmentDirections.FirstToSecond(city, region) // Передаємо місто і регіон
                    findNavController().navigate(action as NavDirections)
                } catch (e: IOException) {
                    editTextCity.error = "Не вдалося отримати дані з Geocoder"
                } catch (e: IllegalArgumentException) {
                    editTextCity.error = e.message
                }
            } else {
                editTextCity.error = "Будь ласка, введіть назву міста"
            }
        }
    }
}