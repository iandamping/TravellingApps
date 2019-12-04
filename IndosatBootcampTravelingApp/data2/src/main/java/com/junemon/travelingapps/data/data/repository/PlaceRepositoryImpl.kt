package com.junemon.travelingapps.data.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.junemon.travelingapps.data.data.datasource.PlaceCacheDataSource
import com.junemon.travelingapps.data.data.datasource.PlaceRemoteDataSource
import com.junemon.travelingapps.data.datasource.model.mapRemoteToCacheDomain
import com.junemon.travellingapps.domain.model.PlaceCacheData
import com.junemon.travellingapps.domain.repository.PlaceRepository

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceRepositoryImpl(private val remoteDataSource: PlaceRemoteDataSource, private val cacheDataSource: PlaceCacheDataSource) : PlaceRepository {

    override fun getCache(): LiveData<List<PlaceCacheData>> {
        return cacheDataSource.getCache().asLiveData()
    }

    override suspend fun delete() {
        cacheDataSource.delete()
    }

    override suspend fun setCache() {
        cacheDataSource.setCache(remoteDataSource.getFirebaseData().mapRemoteToCacheDomain())
    }
}