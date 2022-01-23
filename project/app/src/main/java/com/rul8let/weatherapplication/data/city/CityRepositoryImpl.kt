package com.rul8let.weatherapplication.data.city

import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.city.model.City
import com.rul8let.weatherapplication.data.city.network.CityNetwork
import com.rul8let.weatherapplication.data.city.network.model.CityData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepositoryImpl @Inject constructor(
    private val network: CityNetwork
): CityRepository {

    private val resultWeatherForecastWeek = MutableSharedFlow<Response<List<City>>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun observerCityList(): Flow<Response<List<City>>> = resultWeatherForecastWeek

    override suspend fun getCityList(cityName: String) {
        val response = network.getCityList(cityName)
        when (response){
            is Response.Error -> resultWeatherForecastWeek.emit(Response.Error(response.error))
            is Response.Success -> {
                resultWeatherForecastWeek.emit(Response.Success(mapCity(response.data)))
            }
        }
    }

    private fun mapCity(data: List<CityData>) = data.map { City(
        name = it.name,
        lat = it.lat,
        lon = it.lon,
        country = it.country,
        state = it.state
    ) }

}