package com.example.weatherapp.presentation.second

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.model.ForecastDayData
import com.example.weatherapp.databinding.ItemDayBinding


class DayAdapter: ListAdapter<ForecastDayData, DayAdapter.DayViewHolder>(DayAdapter.Comparator()) {

    class DayViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemDayBinding.bind(view)

        fun bindItem(item: ForecastDayData) {
            binding.dateDay.text = item.date
            binding.dayTempMax.text = item.day.maxTempCelsius.toInt().toString()
            binding.dayTempMin.text = item.day.minTempCelsius.toInt().toString()
            Glide.with(binding.imageDay.context)
                .load("https:" + item.day.condition.icon)
                .into(binding.imageDay)
        }
    }

    class Comparator: DiffUtil.ItemCallback<ForecastDayData>(){
        override fun areItemsTheSame(oldItem: ForecastDayData, newItem: ForecastDayData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ForecastDayData, newItem: ForecastDayData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapter.DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayAdapter.DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayAdapter.DayViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}