package com.example.weatherapp.presentation.second

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.model.HourData
import com.example.weatherapp.data.remote.model.WeatherData
import com.example.weatherapp.databinding.ItemHourBinding

class HourAdapter: ListAdapter<HourData, HourAdapter.HourViewHolder>(Comparator()) {

    class HourViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemHourBinding.bind(view)

        fun bindItem(item: HourData) {
            binding.hourTime.text = item.time
            binding.hourtemp.text = item.temp_c.toInt().toString()
            Glide.with(binding.imageHour.context)
                .load("https:" + item.condition.icon)
                .into(binding.imageHour)
        }
    }

    class Comparator: DiffUtil.ItemCallback<HourData>(){
        override fun areItemsTheSame(oldItem: HourData, newItem: HourData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HourData, newItem: HourData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hour, parent, false)
        return HourViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

}