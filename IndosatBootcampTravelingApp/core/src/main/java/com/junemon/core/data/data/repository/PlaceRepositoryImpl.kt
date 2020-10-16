package com.junemon.core.data.data.repository

import android.net.Uri
import com.junemon.core.data.data.datasource.PlaceCacheDataSource
import com.junemon.core.data.data.datasource.PlaceRemoteDataSource
import com.junemon.core.domain.repository.PlaceRepository
import com.junemon.model.data.dto.mapRemoteToCacheDomain
import com.junemon.model.domain.DataHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
@FlowPreview
class PlaceRepositoryImpl (
    private val remoteDataSource: PlaceRemoteDataSource,
    private val cacheDataSource: PlaceCacheDataSource
) : PlaceRepository {

    /**Observing operation*/
    override fun getRemote(): Flow<Results<List<PlaceCacheData>>> {
        return flow {
            emitAll(remoteDataSource.getFirebaseData().flatMapLatest { firebaseResult ->
                when (firebaseResult) {
                    is DataHelper.RemoteSourceError -> {
                        flowOf(Results.Error(exception = firebaseResult.exception))
                    }

                    is DataHelper.RemoteSourceValue -> {
                        cacheDataSource.setCache(firebaseResult.data.mapRemoteToCacheDomain())
                        cacheDataSource.getCache().map { Results.Success(it) }
                    }
                    else -> {
                        cacheDataSource.getCache().map {
                            Results.Loading(cache = it)
                        }
                    }
                }
            })

        }
    }

    override fun getCache(): Flow<List<PlaceCacheData>> {
        return flow {
            cacheDataSource.getCache().collect { emit(it) }
        }
    }

    override fun getSelectedTypeCache(placeType: String): Flow<List<PlaceCacheData>> {
        return flow {
            cacheDataSource.getSelectedTypeCache(placeType).collect { emit(it) }
        }
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