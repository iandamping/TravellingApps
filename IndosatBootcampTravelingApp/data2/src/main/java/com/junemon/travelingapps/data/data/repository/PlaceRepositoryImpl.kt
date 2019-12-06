package com.junemon.travelingapps.data.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.ian.app.helper.data.ResultToConsume
import com.junemon.travelingapps.data.data.datasource.PlaceCacheDataSource
import com.junemon.travelingapps.data.data.datasource.PlaceRemoteDataSource
import com.junemon.travelingapps.data.datasource.model.mapRemoteToCacheDomain
import com.junemon.travellingapps.domain.model.PlaceCacheData
import com.junemon.travellingapps.domain.model.PlaceRemoteData
import com.junemon.travellingapps.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.map

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceRepositoryImpl(
    private val remoteDataSource: PlaceRemoteDataSource,
    private val cacheDataSource: PlaceCacheDataSource
) : PlaceRepository {
    override fun getCache(): LiveData<ResultToConsume<List<PlaceCacheData>>> {
        return liveData {
            val disposables = emitSource(cacheDataSource.getCache().map {
                ResultToConsume.loading(it)
            }.asLiveData())
            try {
                val responseStatus = remoteDataSource.getFirebaseData().mapRemoteToCacheDomain()
                disposables.dispose()
                check(responseStatus.isNotEmpty())
                cacheDataSource.setCache(responseStatus)
                emitSource(cacheDataSource.getCache().map { ResultToConsume.success(it) }.asLiveData())
            } catch (e: Exception) {
                emitSource(cacheDataSource.getCache().map {
                    ResultToConsume.error(e.message!!, it)
                }.asLiveData())
            }
        }
    }

    override fun getSelectedTypeCache(placeType: String): LiveData<ResultToConsume<List<PlaceCacheData>>> {
        return liveData {
            val disposables = emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                ResultToConsume.loading(it)
            }.asLiveData())
            try {
                val responseStatus = remoteDataSource.getFirebaseData().mapRemoteToCacheDomain()
                disposables.dispose()
                check(responseStatus.isNotEmpty())
                cacheDataSource.setCache(responseStatus)
                emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                    ResultToConsume.success(
                        it
                    )
                }.asLiveData())
            } catch (e: Exception) {
                emitSource(cacheDataSource.getSelectedTypeCache(placeType).map {
                    ResultToConsume.error(e.message!!, it)
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