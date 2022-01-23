package com.rul8let.weatherapplication.di

import com.rul8let.weatherapplication.data.NetworkApi.WEATHER_BASE_URL
import com.rul8let.weatherapplication.data.city.CityRepository
import com.rul8let.weatherapplication.data.city.CityRepositoryImpl
import com.rul8let.weatherapplication.data.city.network.CityNetwork
import com.rul8let.weatherapplication.data.city.network.CityNetworkImpl
import com.rul8let.weatherapplication.data.city.network.retrofit.CityRetrofitApi
import com.rul8let.weatherapplication.data.weather.WeatherRepository
import com.rul8let.weatherapplication.data.weather.WeatherRepositoryImpl
import com.rul8let.weatherapplication.data.weather.network.WeatherNetwork
import com.rul8let.weatherapplication.data.weather.network.WeatherNetworkImpl
import com.rul8let.weatherapplication.data.weather.network.retrofit.WeatherRetrofitApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindNetwork(weatherNetwork: WeatherNetworkImpl): WeatherNetwork

    @Binds
    abstract fun bindData(dataRepository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindCityNetwork(cityNetwork: CityNetworkImpl): CityNetwork

    @Binds
    abstract fun bindCityRepository(cityRepository: CityRepositoryImpl): CityRepository
}

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule{
    @Provides
    @Singleton
    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherRetrofit(retrofit: Retrofit): WeatherRetrofitApi =
        retrofit.create(WeatherRetrofitApi::class.java)

    @Provides
    @Singleton
    fun provideCityRetrofits(retrofit: Retrofit) : CityRetrofitApi =
        retrofit.create(CityRetrofitApi::class.java)
}