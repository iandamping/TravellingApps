package com.junemon.travelingapps.data.data.datasource

import android.net.Uri
import com.ian.app.helper.data.ResultToConsume
import com.junemon.travellingapps.domain.model.PlaceCacheData
import com.junemon.travellingapps.domain.model.PlaceRemoteData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PlaceRemoteDataSource {

    // suspend fun getFirebaseData(): List<PlaceRemoteData>

    suspend fun getFirebaseData(): ResultToConsume<List<PlaceRemoteData>>


    fun setFirebaseData(data: PlaceRemoteData, imageUri: Uri?, success: (Boolean) -> Unit, failed: (Boolean, Throwable) -> Unit)
}

interface PlaceCacheDataSource {

    fun getCache(): Flow<List<PlaceCacheData>>
    fun getSelectedTypeCache(placeType: String): Flow<List<PlaceCacheData>>
    suspend fun setCache(data: List<PlaceCacheData>)
    suspend fun delete()
}