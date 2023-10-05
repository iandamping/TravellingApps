package com.junemon.core.domain.common

interface UsecaseHelper<out T> {
    data class Success<out T>(val data: T) : UsecaseHelper<T>
    data class Error(val errorMessage: String) : UsecaseHelper<Nothing>
}