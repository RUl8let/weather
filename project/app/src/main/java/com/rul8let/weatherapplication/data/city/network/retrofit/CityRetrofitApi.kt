package com.rul8let.weatherapplication.data.city.network.retrofit

import com.rul8let.weatherapplication.data.NetworkApi.WEATHER_API_KEY
import com.rul8let.weatherapplication.data.city.network.model.CityData
import retrofit2.http.GET
import retrofit2.http.Query

interface CityRetrofitApi {

    @GET("geo/1.0/direct")
    suspend fun getCityList(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") key: String = WEATHER_API_KEY
    ) : List<CityData>
}