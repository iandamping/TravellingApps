package com.junemon.core.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PlaceRepository {

    fun getCache(): LiveData<Results<List<PlaceCacheData>>>

    fun getSelectedTypeCache(placeType: String): LiveData<Results<List<PlaceCacheData>>>

    suspend fun delete()

    fun uploadFirebaseData(data: PlaceRemoteData, imageUri: Uri?, success: (Boolean) -> Unit, failed: (Boolean, Throwable) -> Unit)
}