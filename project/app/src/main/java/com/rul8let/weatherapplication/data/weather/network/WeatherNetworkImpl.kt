package com.rul8let.weatherapplication.data.weather.network

import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.weather.network.model.WeatherForecastData
import com.rul8let.weatherapplication.data.weather.network.model.WeatherNow
import com.rul8let.weatherapplication.data.weather.network.retrofit.WeatherRetrofitApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNetworkImpl @Inject constructor(
    private val retrofit: WeatherRetrofitApi
) :WeatherNetwork {
    private val exclude = "current,minutely,hourly,alerts"
    private val metric = "metric"

    override suspend fun getWeather(cityName: String): Response<WeatherNow> {
        val response : Response<WeatherNow> = try {
            Response.Success(retrofit.getWeather(cityName,metric))
        } catch (e: IOException){
            Response.Error(e)
        } catch (e: HttpException){
            Response.Error(e)
        }
        return response
    }

    override suspend fun getWeather(lon: Double, lat: Double): Response<WeatherNow> {
        val response : Response<WeatherNow> = try {
            Response.Success(retrofit.getWeather(lon,lat,metric))
        } catch (e: IOException){
            Response.Error(e)
        } catch (e: HttpException){
            Response.Error(e)
        }
        return response
    }

    override suspend fun getWeatherWeek(lon: Double, lat: Double): Response<WeatherForecastData> {
        val response : Response<WeatherForecastData> = try {
            Response.Success(retrofit.getWeatherWeek(lon,lat,exclude,metric))
        } catch (e: IOException){
            Response.Error(e)
        } catch (e: HttpException){
            Response.Error(e)
        }
        return response
    }

}