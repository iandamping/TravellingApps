package com.junemon.core.data.data.datasource

import android.net.Uri
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PlaceRemoteDataSource {

    suspend fun getFirebaseData(): Results<List<PlaceRemoteData>>

    fun getFlowFirebaseData(): Flow<Results<List<PlaceRemoteData>>>

    fun setFirebaseData(data: PlaceRemoteData, imageUri: Uri?, success: (Boolean) -> Unit, failed: (Boolean, Throwable) -> Unit)
}

interface PlaceCacheDataSource {

    fun getCache(): Flow<List<PlaceCacheData>>
    fun getSelectedTypeCache(placeType: String): Flow<List<PlaceCacheData>>
    suspend fun setCache(data: List<PlaceCacheData>)
    suspend fun delete()
}
