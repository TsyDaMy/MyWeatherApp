package com.example.weatherapp.presentation.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.model.Astro
import com.example.weatherapp.data.remote.model.ConditionData
import com.example.weatherapp.data.remote.model.ForecastDayData
import com.example.weatherapp.data.remote.model.ForecastDayDetailsData
import com.example.weatherapp.data.remote.model.HourData
import com.example.weatherapp.data.remote.model.WeatherData
import com.example.weatherapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    lateinit var binding: FragmentSecondBinding
    lateinit var viewModel: SecondViewModel
    lateinit var adapterH: HourAdapter
    lateinit var adapterD: DayAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout = binding.sw
        viewModel = ViewModelProvider(
            this,
            SecondViewModelFactory(com.example.weatherapp.data.WeatherRepository)
        ).get(SecondViewModel::class.java)

        val views = WeatherViews(
            binding.cityName,
            binding.update,
            binding.ct,
            binding.mincurrenttemp,
            binding.maxcurrenttemp,
            binding.rainchance,
            binding.feellike,
            binding.condition,
            binding.sunrise,
            binding.sunset,
            binding.precip,
            binding.humidity,
            binding.uv,
            binding.winddir,
            binding.windkph,
            binding.viskm,
            binding.pressure,
            binding.sun,
            binding.rvHour,
            binding.sw,
            binding.secondF,
            binding.rvDay,
            binding.region,
            binding.country,
        )

        binding.sun.max = 100

        val city = arguments?.getString("city")
        val region = arguments?.getString("region") ?: "" // Отримуємо регіон, якщо він є

        viewModel.setCity(city, region) // Передаємо місто і регіон до ViewModel

        viewModel.currentWeatherState.observe(viewLifecycleOwner) { weatherState ->
            handleWeatherState(weatherState, views, swipeRefreshLayout)
        }
        viewModel.forecastWeatherState.observe(viewLifecycleOwner) { weatherState ->
            handleWeatherState(weatherState, views, swipeRefreshLayout)
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.setCity(city, region)
        }

        initRvHour()
        initRvDay()
    }

    private fun initRvHour(){
        binding.rvHour.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapterH = HourAdapter()
        binding.rvHour.adapter = adapterH
    }

    private fun initRvDay(){
        binding.rvDay.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapterD = DayAdapter()
        binding.rvDay.adapter = adapterD
    }

    private fun handleWeatherState(
        weatherState: SecondViewModel.WeatherState,
        views: WeatherViews,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        when (weatherState) {
            is SecondViewModel.WeatherState.Loading -> {
                views.setLoadingState()
            }

            is SecondViewModel.WeatherState.Success -> {
                swipeRefreshLayout.isRefreshing = false
                val weatherData = weatherState.weatherData
                updateViews(weatherData, views)
            }

            is SecondViewModel.WeatherState.Error -> {
                swipeRefreshLayout.isRefreshing = false
                views.setErrorState()
                Toast.makeText(requireContext(), weatherState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateViews(
        weatherData: WeatherData,
        views: WeatherViews
    ) {
        views.cityName.text = weatherData.location.name
        views.region.text = weatherData.location.region +","
        views.country.text = weatherData.location.country
        val lastUpdateTime = viewModel.extractTimeFromDateTime(weatherData.current.lastUpdated)
        views.lastUpdate.text = lastUpdateTime
        views.currentTemp.text = weatherData.current.tempCelsius.toInt().toString()
        if (weatherData.current.is_day == 1){
            views.sw.setBackgroundResource(R.color.bgblue)
            views.secondF.setBackgroundResource(R.color.bgblue)
        } else{
            views.sw.setBackgroundResource(R.color.bgblack)
            views.secondF.setBackgroundResource(R.color.bgblack)
        }
        views.feellike.text = weatherData.current.feelsLikeCelsius.toInt().toString()
        views.condition.text = weatherData.current.condition.text
        views.precip.text = weatherData.current.precipMillimeters.toString()
        views.humidity.text = weatherData.current.humidity.toString()
        views.uv.text = weatherData.current.uvIndex.toString()
        views.winddir.text = weatherData.current.windDirection
        views.windkph.text = weatherData.current.windSpeed.toString()
        views.viskm.text = weatherData.current.visibility.toString()
        views.pressure.text = weatherData.current.pressure.toInt().toString()
        val currentTime = weatherData.location.localtime
        val currentTimeMillis = viewModel.getMillisFromTimeString(currentTime)
        val forecastDays = weatherData.forecast?.forecastDays
        if (!forecastDays.isNullOrEmpty()) {
            views.maxCurrentTemp.text = forecastDays[0].day.maxTempCelsius.toInt().toString()
            views.minCurrentTemp.text = forecastDays[0].day.minTempCelsius.toInt().toString()
            views.rainchance.text = forecastDays[0].day.chanceOfRain.toInt().toString()
            val sunriseTime24 = viewModel.convertTo24HourFormat(forecastDays[0].astro.sunrise)
            val sunriseMillis = viewModel.getMillisFrom24HourTime(sunriseTime24)
            val sunsetTime24 = viewModel.convertTo24HourFormat(forecastDays[0].astro.sunset)
            val sunsetMillis = viewModel.getMillisFrom24HourTime(sunsetTime24)
            val progress = (((currentTimeMillis) - sunriseMillis) * 100 / (sunsetMillis - sunriseMillis)).toInt()
            val listH = ArrayList<HourData>()
            for (i in 0 until 24) {
                val hour = weatherData.forecast.forecastDays[0].hour[i]
                val itemH = HourData(
                    viewModel.extractTimeFromDateTime(hour.time),
                    hour.temp_c,
                    ConditionData(hour.condition.text, hour.condition.icon)
                )
                listH.add(itemH)
            }
            adapterH.submitList(listH)
            val listD = ArrayList<ForecastDayData>()
            for (i in 0 until 3) {
                val dayD = weatherData.forecast.forecastDays[i]
                val itemD = ForecastDayData(
                    viewModel.transformDate(dayD.date),
                    Astro("", ""),
                    listOf(HourData("",dayD.hour[0].temp_c,ConditionData("",""))),
                    ForecastDayDetailsData(dayD.day.maxTempCelsius, dayD.day.minTempCelsius, dayD.day.chanceOfRain,
                        ConditionData(dayD.day.condition.text,dayD.day.condition.icon)
                    )
                )
                listD.add(itemD)
            }
            adapterD.submitList(listD)
            views.sunrise.text = sunriseTime24
            views.sunset.text = sunsetTime24
            views.sun.progress = progress

        } else {
            views.maxCurrentTemp.text = "N/A"
            views.minCurrentTemp.text = "N/A"
            views.rainchance.text = "N/A"
            views.sunrise.text = "N/A"
            views.sunset.text = "N/A"
        }
    }

}