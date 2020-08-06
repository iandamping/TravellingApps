package com.junemon.core.data.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.junemon.core.data.data.datasource.PlaceCacheDataSource
import com.junemon.core.data.data.datasource.PlaceRemoteDataSource
import com.junemon.core.domain.repository.PlaceRepository
import com.junemon.model.data.dto.mapRemoteToCacheDomain
import com.junemon.model.domain.DataHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlaceRemoteDataSource,
    private val cacheDataSource: PlaceCacheDataSource
) : PlaceRepository {

    override fun getCache(): Flow<Results<List<PlaceCacheData>>> {
        return flow {
            when (val remoteData = remoteDataSource.getFirebaseData()) {
                is DataHelper.RemoteSourceError -> {
                    emitAll(cacheDataSource.getCache().map {
                        Results.Error(exception = remoteData.exception, cache = it)
                    })
                }
                is DataHelper.RemoteSourceValue -> {
                    cacheDataSource.setCache(remoteData.data.mapRemoteToCacheDomain())
                    emitAll(cacheDataSource.getCache().map { Results.Success(it) })
                }
            }
        }.onStart { emit(Results.Loading) }
    }


    override fun getSelectedTypeCache(placeType: String): Flow<Results<List<PlaceCacheData>>> {
        return flow {
            when (val remoteData = remoteDataSource.getFirebaseData()) {
                is DataHelper.RemoteSourceError -> {
                    emitAll(cacheDataSource.getSelectedTypeCache(placeType).map {
                        Results.Error(exception = remoteData.exception, cache = it)
                    })
                }
                is DataHelper.RemoteSourceValue -> {
                    cacheDataSource.setCache(remoteData.data.mapRemoteToCacheDomain())
                    emitAll(cacheDataSource.getSelectedTypeCache(placeType).map { Results.Success(it) })
                }
            }
        }.onStart { emit(Results.Loading) }
    }

    override suspend fun delete() {
        cacheDataSource.delete()
    }

    override fun uploadFirebaseData(
        data: PlaceRemoteData,
        imageUri: Uri?,
        success: (Boolean) -> Unit,
        failed: (Boolean, Throwable) -> Unit
    ) {
        remoteDataSource.setFirebaseData(data, imageUri, success, failed)
    }
}