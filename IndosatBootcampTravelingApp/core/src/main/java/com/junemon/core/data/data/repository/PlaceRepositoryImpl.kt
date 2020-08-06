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
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
@FlowPreview
class PlaceRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlaceRemoteDataSource,
    private val cacheDataSource: PlaceCacheDataSource
) : PlaceRepository {

    /**One shot operation*/
    override fun getCacheOneShot(): Flow<Results<List<PlaceCacheData>>> {
        return flow {
            when (val remoteData = remoteDataSource.getFirebaseOneShotData()) {
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

    /**Observing operation*/
    override fun getCache(): Flow<Results<List<PlaceCacheData>>> {
        return flow {
            emitAll(remoteDataSource.getFirebaseData().flatMapLatest { firebaseResult ->
                when (firebaseResult) {
                    is DataHelper.RemoteSourceError -> {
                        cacheDataSource.getCache().map {
                            Results.Error(exception = firebaseResult.exception, cache = it)
                        }
                    }

                    is DataHelper.RemoteSourceValue -> {
                        cacheDataSource.setCache(firebaseResult.data.mapRemoteToCacheDomain())
                        cacheDataSource.getCache().map { Results.Success(it) }
                    }
                }
            })

        }.onStart { emit(Results.Loading) }
    }

    override fun getSelectedTypeCache(placeType: String): Flow<Results<List<PlaceCacheData>>> {
        return flow {
            emitAll(remoteDataSource.getFirebaseData().flatMapLatest { firebaseResult ->
                when (firebaseResult) {
                    is DataHelper.RemoteSourceError -> {
                        cacheDataSource.getSelectedTypeCache(placeType).map {
                            Results.Error(exception = firebaseResult.exception, cache = it)
                        }
                    }

                    is DataHelper.RemoteSourceValue -> {
                        cacheDataSource.setCache(firebaseResult.data.mapRemoteToCacheDomain())
                        cacheDataSource.getSelectedTypeCache(placeType).map { Results.Success(it) }
                    }
                }
            })

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