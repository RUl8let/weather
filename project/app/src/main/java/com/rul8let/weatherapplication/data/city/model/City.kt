package com.rul8let.weatherapplication.data.city.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City (
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
) : Parcelable
