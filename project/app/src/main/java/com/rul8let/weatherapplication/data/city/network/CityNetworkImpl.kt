package com.rul8let.weatherapplication.data.city.network

import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.city.network.model.CityData
import com.rul8let.weatherapplication.data.city.network.retrofit.CityRetrofitApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityNetworkImpl @Inject constructor(
    private val retrofit: CityRetrofitApi
) : CityNetwork {

    override suspend fun getCityList(cityName: String): Response<List<CityData>> {
        val response : Response<List<CityData>> = try {
            Response.Success(retrofit.getCityList(cityName = cityName))
        } catch (e: IOException){
            Response.Error(e)
        } catch (e: HttpException){
            Response.Error(e)
        }
        return response
    }

}