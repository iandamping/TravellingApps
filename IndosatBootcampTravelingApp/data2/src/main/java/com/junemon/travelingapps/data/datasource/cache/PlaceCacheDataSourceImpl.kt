package com.junemon.travelingapps.data.datasource.cache

import com.junemon.travelingapps.data.data.datasource.PlaceCacheDataSource
import com.junemon.travelingapps.data.datasource.model.mapToDatabase
import com.junemon.travelingapps.data.datasource.model.mapToDomain
import com.junemon.travelingapps.data.db.PlaceDatabase
import com.junemon.travellingapps.domain.model.PlaceCacheData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceCacheDataSourceImpl(private val db: PlaceDatabase) : PlaceCacheDataSource {

    override fun getCache(): Flow<List<PlaceCacheData>> {
        return db.placeDao().loadAllPlace().mapToDomain()
    }

    override suspend fun setCache(data: List<PlaceCacheData>) {
        db.placeDao().insertAllPlace(*data.mapToDatabase().toTypedArray())
    }

    override suspend fun delete() {
        db.placeDao().deleteAllPlace()
    }
}