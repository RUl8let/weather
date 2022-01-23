package com.rul8let.weatherapplication.data.city.network.model

import com.google.gson.annotations.SerializedName

data class CityData (
    @SerializedName("name")
    val name: String,

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double,

    @SerializedName("country")
    val country: String,

    @SerializedName("state")
    val state: String?
    )