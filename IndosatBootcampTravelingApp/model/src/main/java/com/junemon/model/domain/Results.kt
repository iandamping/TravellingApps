package com.junemon.model.domain

sealed class Results<out R> {
    data class Success<out T>(val data: T) : Results<T>()
    object Loading : Results<Nothing>()
    data class Error(val exception: Exception) : Results<Nothing>()
}
