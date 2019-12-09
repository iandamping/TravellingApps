package com.junemon.gamesapi.data.data.repository

import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import com.ian.app.helper.interfaces.ExtractResultHelper
import com.junemon.gamesapi.data.data.datasource.PublisherCacheDataSource
import com.junemon.gamesapi.data.data.datasource.PublisherRemoteDataSource
import com.junemon.gamesapi.data.datasource.model.mapToDatabase
import com.junemon.gamesapi.domain2.model.PublisherData
import com.junemon.gamesapi.domain2.repository.PublisherRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PublisherRepositoryImpl(
    private val remoteDataSource: PublisherRemoteDataSource,
    private val cacheDataSource: PublisherCacheDataSource
) : PublisherRepository,KoinComponent {
    private val cachingResult: ExtractResultHelper by inject()
    override fun get(): LiveData<ResultToConsume<List<PublisherData>>> {
        return cachingResult.ssotLiveDataResult(
            databaseQuery = { cacheDataSource.get() },
            networkCall = { remoteDataSource.get() },
            saveCallResult = { cacheDataSource.set(it.mapToDatabase()) }
        )
    }
}