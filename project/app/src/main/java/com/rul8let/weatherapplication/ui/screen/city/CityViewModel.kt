package com.rul8let.weatherapplication.ui.screen.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.city.CityRepository
import com.rul8let.weatherapplication.data.city.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityRepository: CityRepository
) : ViewModel(){

    private val _cityList = MutableLiveData<Response<List<City>>>()
    val cityList : LiveData<Response<List<City>>> = _cityList

    init {
        viewModelScope.launch {
            cityRepository.observerCityList().collect {
                _cityList.value = it
            }
        }
    }

    fun getList(cityName: String) {
        viewModelScope.launch {
            cityRepository.getCityList(cityName)
        }
    }
}