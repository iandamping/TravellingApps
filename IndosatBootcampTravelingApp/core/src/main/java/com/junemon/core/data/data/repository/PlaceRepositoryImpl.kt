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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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

    override fun getCache(): LiveData<Results<List<PlaceCacheData>>> {
        return liveData {
            val disposables = emitSource(flowOf(Results.Loading).asLiveData())
            when (val remoteData = remoteDataSource.getFirebaseData()) {
                is DataHelper.RemoteSourceError -> {
                    disposables.dispose()
                    emitSource(cacheDataSource.getCache().map {
                        Results.Error(exception = remoteData.exception, cache = it)
                    }.asLiveData())
                }
                is DataHelper.RemoteSourceValue -> {
                    disposables.dispose()
                    cacheDataSource.setCache(remoteData.data.mapRemoteToCacheDomain())
                    emitSource(cacheDataSource.getCache().map { Results.Success(it) }.asLiveData())
                }
            }
        }
    }

    override fun getSelectedTypeCache(placeType: String): LiveData<Results<List<PlaceCacheData>>> {
        return liveData {
            val disposables = emitSource(flowOf(Results.Loading).asLiveData())
            when (val remoteData = remoteDataSource.getFirebaseData()) {
                is DataHelper.RemoteSourceError -> {
                    disposables.dispose()
                    emitSource(
                        cacheDataSource.getSelectedTypeCache(placeType)
                            .map {
                                Results.Error(exception = remoteData.exception, cache = it)
                            }.asLiveData()
                    )
                }
                is DataHelper.RemoteSourceValue -> {
                    disposables.dispose()
                    cacheDataSource.setCache(remoteData.data.mapRemoteToCacheDomain())
                    emitSource(
                        cacheDataSource.getSelectedTypeCache(placeType).map {
                            Results.Success(it)
                        }.asLiveData()
                    )
                }
            }
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