package com.rul8let.weatherapplication.data.weather.network.retrofit

import com.rul8let.weatherapplication.data.NetworkApi.WEATHER_API_KEY
import com.rul8let.weatherapplication.data.weather.network.model.WeatherForecastData
import com.rul8let.weatherapplication.data.weather.network.model.WeatherNow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRetrofitApi {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") key: String = WEATHER_API_KEY
    ): WeatherNow

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") key: String = WEATHER_API_KEY
    ): WeatherNow


    @GET("data/2.5/onecall")
    suspend fun getWeatherWeek(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("appid") key: String = WEATHER_API_KEY
    ): WeatherForecastData
}