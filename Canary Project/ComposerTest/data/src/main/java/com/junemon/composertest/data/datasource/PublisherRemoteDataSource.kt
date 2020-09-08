package com.junemon.gamesapi.data.data.datasource

import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.data.datasource.model.PublishersDbEntity
import com.junemon.gamesapi.domain2.model.PublisherData
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PublisherRemoteDataSource {
    fun get(): Single<List<PublisherData>>
}


interface PublisherCacheDataSource {

    fun set(data: List<PublishersDbEntity>)

    fun get(): Single<List<PublisherData>>
}