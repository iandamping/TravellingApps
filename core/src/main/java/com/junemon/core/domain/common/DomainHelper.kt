package com.junemon.core.domain.common

sealed interface DomainHelper<out T> {
    data class Success<out T>(val data: T) : DomainHelper<T>
    data class Error(val errorMessage: String) : DomainHelper<Nothing>
}