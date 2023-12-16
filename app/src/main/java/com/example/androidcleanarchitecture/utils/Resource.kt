package com.example.androidcleanarchitecture.utils

sealed class Resource<T>(val data: T) {
    class Loading<T>(data: T? = null) : Resource<T?>(data)
    class Success<T>(data: T?) : Resource<T?>(data)
    class Error<T>(error: T? = null) : Resource<T?>(error)
    class SetError<T>(setError: T? = null) : Resource<T?>(setError)

}