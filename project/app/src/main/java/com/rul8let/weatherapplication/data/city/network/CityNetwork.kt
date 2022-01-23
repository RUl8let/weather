package com.rul8let.weatherapplication.data.city.network

import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.city.network.model.CityData

interface CityNetwork {

    suspend fun getCityList(cityName: String) : Response<List<CityData>>
}