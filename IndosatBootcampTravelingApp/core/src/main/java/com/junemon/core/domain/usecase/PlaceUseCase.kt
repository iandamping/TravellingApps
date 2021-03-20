package com.junemon.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results

/**
 * Created by Ian Damping on 20,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PlaceUseCase {

    fun getCache(): LiveData<List<PlaceCacheData>>

    fun getRemote(): LiveData<Results<List<PlaceCacheData>>>

    fun getSelectedTypeCache(placeType: String): LiveData<List<PlaceCacheData>>

    suspend fun delete()

    fun uploadFirebaseData(
        data: PlaceRemoteData,
        imageUri: Uri?,
        success: (Boolean) -> Unit,
        failed: (Boolean, Throwable) -> Unit
    )
}