package com.rul8let.weatherapplication.ui.adapter.forecast

import androidx.recyclerview.widget.DiffUtil
import com.rul8let.weatherapplication.data.weather.model.WeatherForecast

class ForecastDiffUtil : DiffUtil.ItemCallback<WeatherForecast>() {
    override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
        return oldItem == newItem
    }
}