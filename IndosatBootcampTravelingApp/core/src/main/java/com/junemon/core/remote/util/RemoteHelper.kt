package com.junemon.core.remote.util

import android.net.Uri
import com.junemon.model.domain.DataHelper
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface RemoteHelper {

    suspend fun getFirebaseData(): Flow<DataHelper<List<PlaceRemoteData>>>

    fun getFlowFirebaseData(): Flow<DataHelper<List<PlaceRemoteData>>>

    fun uploadFirebaseData(
        data: PlaceRemoteData,
        imageUri: Uri?,
        success: (Boolean) -> Unit,
        failed: (Boolean, Throwable) -> Unit
    )
}