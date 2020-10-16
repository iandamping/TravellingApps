package com.junemon.core.data.datasource.cache

import com.junemon.core.cache.util.dto.mapToDatabase
import com.junemon.core.cache.util.dto.mapToDomain
import com.junemon.core.cache.util.interfaces.PlacesDaoHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.core.data.data.datasource.PlaceCacheDataSource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceCacheDataSourceImpl(private val placeDao: PlacesDaoHelper) :
    PlaceCacheDataSource {

    override fun getCache(): Flow<List<PlaceCacheData>> {
        return placeDao.loadAllPlace().mapToDomain()
    }

    override fun getSelectedTypeCache(placeType: String): Flow<List<PlaceCacheData>> {
        return placeDao.loadAllBalanceByMonth(placeType).mapToDomain()
    }

    override suspend fun setCache(data: List<PlaceCacheData>) {
        placeDao.insertAllPlace(*data.mapToDatabase().toTypedArray())
    }

    override suspend fun delete() {
        placeDao.deleteAllPlace()
    }
}