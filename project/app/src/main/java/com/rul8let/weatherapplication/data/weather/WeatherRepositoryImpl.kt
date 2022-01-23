package com.rul8let.weatherapplication.data.weather

import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.weather.model.WeatherForecast
import com.rul8let.weatherapplication.data.weather.model.WeatherInformation
import com.rul8let.weatherapplication.data.weather.network.WeatherNetwork
import com.rul8let.weatherapplication.data.weather.network.model.WeatherForecastData
import com.rul8let.weatherapplication.data.weather.network.model.WeatherNow
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import java.text.SimpleDateFormat
import java.util.*

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val network: WeatherNetwork
) : WeatherRepository {

    private val resultWeatherToday = MutableSharedFlow<Response<WeatherInformation>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val resultWeatherForecastWeek = MutableSharedFlow<Response<List<WeatherForecast>>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun observeWeatherNowResult(): Flow<Response<WeatherInformation>> = resultWeatherToday
    override suspend fun observeWeatherForecastWeek(): Flow<Response<List<WeatherForecast>>> = resultWeatherForecastWeek

    override suspend fun getWeather(cityName: String) {
        when (val response = network.getWeather(cityName)){
            is Response.Error -> resultWeatherToday.emit(Response.Error(response.error))
            is Response.Success -> {
                resultWeatherToday.emit(Response.Success(mapWeatherNow(data = response.data)))
            }
        }
    }

    override suspend fun getWeather(lat: Double, lon: Double) {
        when (val response = network.getWeather(lon,lat)){
            is Response.Error -> resultWeatherToday.emit(Response.Error(response.error))
            is Response.Success -> {
                resultWeatherToday.emit(Response.Success(mapWeatherNow(data = response.data)))
            }
        }
    }

    override suspend fun getWeatherForecastWeek(lat: Double, lon: Double) {
        when (val response = network.getWeatherWeek(lon,lat)){
            is Response.Error -> resultWeatherForecastWeek.emit(Response.Error(response.error))
            is Response.Success -> {
                resultWeatherForecastWeek.emit(Response.Success(mapForecast(data = response.data)))
            }
        }
    }

    private fun mapWeatherNow(data : WeatherNow): WeatherInformation {
        return WeatherInformation(
            cityName = data.name,
            lon = data.coord.lon,
            lat = data.coord.lat,
            tempToday = data.main.temp.toInt(),
            icon = data.weather.first().icon,
            iconDescription = data.weather.first().description
        )
    }

    private fun mapForecast(data: WeatherForecastData) : List<WeatherForecast>{
        val list = data.daily.map { it -> WeatherForecast(
            data = converterDateUnix(it.dt.toLong()),
            temp = it.temp.day.toInt(),
            icon = it.weather.first().icon,
            iconDescription = it.weather.first().description
        ) }
        return list
    }

    //Конвертирует unix дату.
    // Возвращает строку в следюущем виде "mon. 11" (mon - Monday)
    private fun converterDateUnix(dateUnix: Long): String{
        val date = Date(dateUnix * 1000)
        return SimpleDateFormat("EEE',' d", Locale.US).format(date)
    }
}