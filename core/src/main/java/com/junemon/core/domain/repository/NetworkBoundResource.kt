package com.junemon.core.domain.repository

import com.junemon.core.domain.common.DomainHelper
import com.junemon.core.domain.common.DataHelper
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<DomainHelper<ResultType>> = flow {
        val dbSource = loadFromDB().first()


        if (shouldFetch(dbSource)) {
            when (val apiResponse = createCall().first()) {

                is DataHelper.Success -> {
                    saveCallResult(apiResponse.data)

                    emitAll(loadFromDB().map {  result ->
                        DomainHelper.Success(
                            result
                        )
                    })
                }

                is DataHelper.Error -> {
                    onFetchFailed()
                    emit(
                        DomainHelper.Error(
                            apiResponse.exception.localizedMessage
                                ?: "Application encounter unknown error"
                        )
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map { result ->
                DomainHelper.Success(
                    result
                )
            })
        }

    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<DataHelper<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<DomainHelper<ResultType>> = result
}