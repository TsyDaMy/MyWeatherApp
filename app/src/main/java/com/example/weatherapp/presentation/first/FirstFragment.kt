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
        val editText = binding.cityEditText
        binding.confirmButton.setOnClickListener {
            val text = editText.text.toString().trim()

            if (text.isNotBlank()) {
                try {
                    viewModel.checkCityName(text, geocoder)
                    viewModel.setSelectedCity(text)
                    val action = FirstFragmentDirections.FirstToSecond(text)
                    findNavController().navigate(action)
                } catch (e: IOException) {
                    editText.error = "Не вдалося отримати дані з Geocoder"
                } catch (e: IllegalArgumentException) {
                    editText.error = e.message
                }
            } else {
                editText.error = "Будь ласка, введіть назву міста"
            }
        }
    }
}