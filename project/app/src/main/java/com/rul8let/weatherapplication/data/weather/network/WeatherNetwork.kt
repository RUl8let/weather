package com.rul8let.weatherapplication.data.weather.network

import com.rul8let.weatherapplication.data.Response

import com.rul8let.weatherapplication.data.weather.network.model.WeatherForecastData
import com.rul8let.weatherapplication.data.weather.network.model.WeatherNow

interface WeatherNetwork {
    suspend fun getWeather(cityName: String) : Response<WeatherNow>
    suspend fun getWeather(lon: Double,lat: Double) : Response<WeatherNow>
    suspend fun getWeatherWeek(lon: Double,lat: Double): Response<WeatherForecastData>
}