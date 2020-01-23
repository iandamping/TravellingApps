package com.junemon.core.data.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.junemon.core.data.data.datasource.PlaceCacheDataSource
import com.junemon.core.data.data.datasource.PlaceRemoteDataSource
import com.junemon.core.data.di.IoDispatcher
import com.junemon.core.domain.repository.PlaceRepository
import com.junemon.model.data.dto.mapRemoteToCacheDomain
import com.junemon.model.domain.DataHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val remoteDataSource: PlaceRemoteDataSource,
    private val cacheDataSource: PlaceCacheDataSource
) : PlaceRepository {
    override fun getCache(): LiveData<Results<List<PlaceCacheData>>> {
        return liveData(ioDispatcher) {
            val disposables = emitSource(cacheDataSource.getCache().map {
                Results.Loading
            }.asLiveData())

            val responseStatus = remoteDataSource.getFlowFirebaseData()

            responseStatus.collect { data ->
                when (data) {
                    is DataHelper.RemoteSourceError -> {
                        emitSource(cacheDataSource.getCache().map {
                            Results.Error(exception = data.exception, cache = it)
                        }.asLiveData())
                    }
                    is DataHelper.RemoteSourceValue -> {
                        check(data.data.isNotEmpty()) {
                            " data is empty "
                        }
                        // Stop the previous emission to avoid dispatching the updated user
                        // as `loading`.
                        disposables.dispose()
                        cacheDataSource.setCache(data.data.mapRemoteToCacheDomain())
                        emitSource(cacheDataSource.getCache().map { Results.Success(it) }.asLiveData())
                    }
                }
            }

        }
    }

    override fun getSelectedTypeCache(placeType: String): LiveData<Results<List<PlaceCacheData>>> {
        return liveData(ioDispatcher) {

            val disposables = emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                Results.Loading
            }.asLiveData())

            when (val responseStatus = remoteDataSource.getFirebaseData()) {
                is DataHelper.RemoteSourceValue -> {
                    check(responseStatus.data.isNotEmpty()) {
                        " data is null "
                    }
                    // Stop the previous emission to avoid dispatching the updated user
                    // as `loading`.
                    disposables.dispose()
                    cacheDataSource.setCache(responseStatus.data.mapRemoteToCacheDomain())
                    emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                        Results.Success(it)
                    }.asLiveData())
                }
                is DataHelper.RemoteSourceError -> {
                    emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                        Results.Error(exception = responseStatus.exception, cache = it)
                    }.asLiveData())
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