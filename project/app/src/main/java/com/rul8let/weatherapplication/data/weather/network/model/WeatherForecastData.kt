package com.rul8let.weatherapplication.data.weather.network.model

import com.google.gson.annotations.SerializedName

data class WeatherForecastData(
    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double,

    @SerializedName("timezone")
    val timezone: String,

    @SerializedName("timezone_offset")
    val timezoneOffset: Int,

    @SerializedName("daily")
    val daily: List<Daily>
    )

data class Daily(
    @SerializedName("dt")
    val dt: Int,

    @SerializedName("temp")
    val temp: Temp,

    @SerializedName("weather")
    val weather: List<WeatherForeCastS>
)

data class Temp(
    @SerializedName("day")
    val day: Double
)

data class WeatherForeCastS (
    @SerializedName("id")
    val id: Int,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
)