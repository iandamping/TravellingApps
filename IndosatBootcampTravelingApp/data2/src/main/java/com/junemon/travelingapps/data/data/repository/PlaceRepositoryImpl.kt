package com.junemon.travelingapps.data.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.junemon.model.data.dto.mapRemoteToCacheDomain
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import com.junemon.travelingapps.data.data.datasource.PlaceCacheDataSource
import com.junemon.travelingapps.data.data.datasource.PlaceRemoteDataSource
import com.junemon.travelingapps.data.di.DefaultDispatcher
import com.junemon.travelingapps.data.di.IoDispatcher
import com.junemon.travellingapps.domain.repository.PlaceRepository
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
            try {
                val responseStatus = remoteDataSource.getFlowFirebaseData()
                responseStatus.collect { data ->
                    when (data) {
                        is Results.Error -> {
                            emitSource(cacheDataSource.getCache().map {
                                Results.Error(data.exception)
                            }.asLiveData())
                        }
                        is Results.Success -> {
                            check(data.data.isNotEmpty()) {
                                " data is empty "
                            }
                            cacheDataSource.setCache(data.data.mapRemoteToCacheDomain())
                            emitSource(cacheDataSource.getCache().map { Results.Success(it) }.asLiveData())
                        }
                        is Results.Loading -> {
                            emitSource(cacheDataSource.getCache().map {
                                Results.Loading
                            }.asLiveData())
                        }
                    }
                }
            } catch (e: Exception) {
                emitSource(cacheDataSource.getCache().map {
                    Results.Error(e)
                }.asLiveData())
            }
        }
    }

    override fun getSelectedTypeCache(placeType: String): LiveData<Results<List<PlaceCacheData>>> {
        return liveData(ioDispatcher) {

            try {
                when (val responseStatus = remoteDataSource.getFirebaseData()) {
                    is Results.Success -> {
                        check(responseStatus.data.isNotEmpty()) {
                            " data is null "
                        }
                        cacheDataSource.setCache(responseStatus.data.mapRemoteToCacheDomain())
                        emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                            Results.Success(it)
                        }.asLiveData())
                    }
                    is Results.Loading -> {
                        emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                            Results.Loading
                        }.asLiveData())
                    }
                    is Results.Error -> {
                        emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                            Results.Error(responseStatus.exception)
                        }.asLiveData())
                    }
                }
            } catch (e: Exception) {
                emitSource(cacheDataSource.getCache().map {
                    Results.Error(e)
                }.asLiveData())
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