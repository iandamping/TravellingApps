package com.junemon.model.domain

sealed class Results<out R> {
    data class Success<out T>(val data: T) : Results<T>()
    data class Loading<out T>(val cache:T?) : Results<T>()
    data class Error(val exception: Exception) : Results<Nothing>()
}


sealed class DataHelper<out T>{
    data class RemoteSourceValue<out T>(val data: T) : DataHelper<T>()
    data class RemoteSourceError(val exception: Exception) : DataHelper<Nothing>()
    object RemoteSourceLoading : DataHelper<Nothing>()
}

sealed class FirebaseLoginDataHelper<out T>{
    data class RemoteSourceValue<out T>(val data: T) : FirebaseLoginDataHelper<T>()
    data class RemoteSourceError(val exception: Exception) : FirebaseLoginDataHelper<Nothing>()
}


sealed class UserResult<out R> {
    data class Success<out T>(val data: T) : UserResult<T>()
    data class Error(val exception: Exception) : UserResult<Nothing>()
}
