package com.junemon.travellingapps.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import com.junemon.travellingapps.domain.model.PlaceCacheData
import com.junemon.travellingapps.domain.model.PlaceRemoteData

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PlaceRepository {

    fun getCache(): LiveData<ResultToConsume<List<PlaceCacheData>>>

    fun getSelectedTypeCache(placeType: String): LiveData<ResultToConsume<List<PlaceCacheData>>>

    suspend fun delete()

    fun uploadFirebaseData(data: PlaceRemoteData, imageUri: Uri?, success: (Boolean) -> Unit, failed: (Boolean, Throwable) -> Unit)
}