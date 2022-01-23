package com.rul8let.weatherapplication.ui.adapter.forecast

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rul8let.weatherapplication.data.weather.model.WeatherForecast

class ForecastAdapter (): ListAdapter<WeatherForecast, RecyclerView.ViewHolder>(ForecastDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeatherHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is WeatherHolder-> holder.bind(currentList[position])
        }
    }
}