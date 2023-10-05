package com.junemon.core.domain.common

sealed interface DataHelper<out T> {
    data class Success<out T>(val data: T) : DataHelper<T>
    data class Error(val exception: Exception) : DataHelper<Nothing>
}
