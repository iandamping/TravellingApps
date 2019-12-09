package com.junemon.gamesapi.data.datasource.remote

import com.ian.app.helper.base.BaseDataSource
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.data.data.datasource.PublisherRemoteDataSource
import com.junemon.gamesapi.data.datasource.model.mapRemoteItemToDomain
import com.junemon.gamesapi.domain2.model.PublisherData
import kotlinx.coroutines.CompletableDeferred

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PublishersRemoteDataSourceImpl(private val api:GamesApi): BaseDataSource(),PublisherRemoteDataSource {
    override suspend fun get(): ResultToConsume<List<PublisherData>> {
        val results = CompletableDeferred<ResultToConsume<List<PublisherData>>>()
        try {
            val firstData = api.getPublisher().doOneShot()
            val firstDataMap = firstData.data?.data?.mapRemoteItemToDomain()
            checkNotNull(firstDataMap){
                " ${firstData.message} "
            }
            assert(firstDataMap.isNotEmpty())
            results.complete(ResultToConsume(firstData.status, firstDataMap, firstData.message))
        } catch (e: Exception) {
            results.complete(ResultToConsume(ResultToConsume.Status.ERROR, null, e.message))
        }
        return results.await()
    }
}