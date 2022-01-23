package com.rul8let.weatherapplication.ui.adapter.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rul8let.weatherapplication.R
import com.rul8let.weatherapplication.data.weather.model.WeatherForecast
import com.rul8let.weatherapplication.databinding.WeatherCardBinding

class WeatherHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    private val binding = WeatherCardBinding.bind(itemView)

    fun bind(weatherWeek: WeatherForecast) {
        Glide.with(binding.root.context).load("https://openweathermap.org/img/wn/${weatherWeek.icon}@2x.png").into(binding.weatherIcon)
        binding.dataText.text=weatherWeek.data
        binding.tempDay.text= binding.root.context.getString(R.string.temp,weatherWeek.temp.toString())
    }

    companion object {
        fun create(parent: ViewGroup) : WeatherHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view= layoutInflater.inflate(R.layout.weather_card,parent,false)
            return WeatherHolder(view)
        }
    }
}