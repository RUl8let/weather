package com.rul8let.weatherapplication.data

sealed class Response<out R> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val error: java.lang.Exception) : Response<Nothing>()
}