package com.junemon.travelingapps.data.datasource.cache

import com.junemon.cache.util.dto.mapToDatabase
import com.junemon.cache.util.dto.mapToDomain
import com.junemon.cache.util.interfaces.PlacesDaoHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.travelingapps.data.data.datasource.PlaceCacheDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceCacheDataSourceImpl @Inject constructor(private val placeDao: PlacesDaoHelper) : PlaceCacheDataSource {

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