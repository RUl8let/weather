package com.rul8let.weatherapplication.data.weather.model

data class WeatherInformation (
    val cityName : String,
    val lon : Double,
    val lat : Double,
    val tempToday : Int,
    val icon : String,
    val iconDescription: String
    )