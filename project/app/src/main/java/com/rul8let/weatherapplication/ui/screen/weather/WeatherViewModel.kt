package com.rul8let.weatherapplication.ui.screen.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rul8let.weatherapplication.data.weather.WeatherRepository
import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.weather.model.WeatherForecast
import com.rul8let.weatherapplication.data.weather.model.WeatherInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherNow = MutableLiveData<Response<WeatherInformation>>()
    val weatherNow : LiveData<Response<WeatherInformation>> = _weatherNow

    private val _weatherWeek = MutableLiveData<Response<List<WeatherForecast>>>()
    val weatherWeek : LiveData<Response<List<WeatherForecast>>> = _weatherWeek

    init {
        viewModelScope.launch {
            repository.observeWeatherNowResult().collect {
                _weatherNow.value=it
            }
        }
        viewModelScope.launch {
            repository.observeWeatherForecastWeek().collect {
                _weatherWeek.value=it
            }
        }
    }

    fun getWeather(cityName: String) {
        viewModelScope.launch {
            repository.getWeather(cityName)
        }
    }

    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            repository.getWeather(lat,lon)
        }
    }

    fun getWeatherWeek(lat: Double, lon: Double) {
        viewModelScope.launch {
            repository.getWeatherForecastWeek(lat,lon)
        }
    }

}