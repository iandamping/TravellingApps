package com.junemon.travellingapps.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import com.junemon.travellingapps.domain.model.PlaceCacheData
import com.junemon.travellingapps.domain.model.PlaceRemoteData
import com.junemon.travellingapps.domain.repository.PlaceRepository

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceUseCase(private val repository: PlaceRepository) {

    fun getCache(): LiveData<ResultToConsume<List<PlaceCacheData>>> = repository.getCache()
    fun getSelectedTypeCache(placeType: String): LiveData<ResultToConsume<List<PlaceCacheData>>> = repository.getSelectedTypeCache(placeType)
    suspend fun delete() = repository.delete()
    fun uploadFirebaseData(data: PlaceRemoteData, imageUri: Uri?, success: (Boolean) -> Unit, failed: (Boolean, Throwable) -> Unit) = repository.uploadFirebaseData(data, imageUri, success, failed)
}