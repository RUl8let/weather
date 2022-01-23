package com.rul8let.weatherapplication.data.city

import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.city.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun observerCityList(): Flow<Response<List<City>>>

    suspend fun getCityList(cityName: String)
}