package com.junemon.model.domain

sealed class Results<out R> {
    data class Success<out T>(val data: T) : Results<T>()
    object Loading : Results<Nothing>()
    data class Error<out T>(val exception: Exception,val cache:T?) : Results<T>()
}

sealed class DataHelper<out T>{
    data class RemoteSourceValue<out T>(val data: T) : DataHelper<T>()
    data class RemoteSourceError(val exception: Exception) : DataHelper<Nothing>()
}
