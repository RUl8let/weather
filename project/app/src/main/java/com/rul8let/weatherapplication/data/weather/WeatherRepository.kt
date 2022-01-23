package com.rul8let.weatherapplication.data.weather

import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.weather.model.WeatherForecast
import com.rul8let.weatherapplication.data.weather.model.WeatherInformation
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun observeWeatherNowResult(): Flow<Response<WeatherInformation>>

    suspend fun observeWeatherForecastWeek(): Flow<Response<List<WeatherForecast>>>

    suspend fun getWeather(cityName: String)

    suspend fun getWeather(lat:Double,lon:Double)

    suspend fun getWeatherForecastWeek(lat:Double,lon:Double)
}