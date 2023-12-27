package com.example.tbc_better_login_davaleba17.data.common

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val errorMessage: String) : Resource<T>()
    //loading is not being used at the moment
    data class Loading<T>(var loading: Boolean) : Resource<T>()
}