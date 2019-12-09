package com.junemon.gamesapi.data.datasource.cache

import com.junemon.gamesapi.data.data.datasource.PublisherCacheDataSource
import com.junemon.gamesapi.data.datasource.model.PublishersDbEntity
import com.junemon.gamesapi.data.datasource.model.mapToDomain
import com.junemon.gamesapi.data.db.GameDatabase
import com.junemon.gamesapi.domain2.model.PublisherData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PublisherCaceDataSourceImpl(private val db:GameDatabase):PublisherCacheDataSource {

    override suspend fun set(data: List<PublishersDbEntity>) {
        db.publisherDao().insertGame(*data.toTypedArray())
    }

    override fun get(): Flow<List<PublisherData>> {
        return db.publisherDao().loadGame().mapToDomain()
    }
}