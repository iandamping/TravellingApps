package com.junemon.core.domain.repository

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
interface PlaceRepository {

    fun getRemote(): Flow<Results<List<PlaceCacheData>>>

    fun getCache(): Flow<List<PlaceCacheData>>

    fun getSelectedTypeCache(placeType: String): Flow<List<PlaceCacheData>>

    suspend fun delete()

    fun uploadFirebaseData(
        data: PlaceRemoteData,
        imageUri: Uri?,
        success: (Boolean) -> Unit,
        failed: (Boolean, Throwable) -> Unit
    )
}