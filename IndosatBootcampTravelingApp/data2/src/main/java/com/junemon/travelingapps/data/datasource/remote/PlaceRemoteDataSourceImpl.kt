package com.junemon.travelingapps.data.datasource.remote

import android.net.Uri
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import com.junemon.remote.RemoteHelper
import com.junemon.travelingapps.data.data.datasource.PlaceRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceRemoteDataSourceImpl @Inject constructor(
    private val remoteHelper: RemoteHelper
) : PlaceRemoteDataSource {

    @ExperimentalCoroutinesApi
    override suspend fun getFirebaseData(): Results<List<PlaceRemoteData>> {
        return remoteHelper.getFirebaseData()
    }

    @ExperimentalCoroutinesApi
    override fun getFlowFirebaseData(): Flow<Results<List<PlaceRemoteData>>> {
        return remoteHelper.getFlowFirebaseData()
    }

    override fun setFirebaseData(
        data: PlaceRemoteData,
        imageUri: Uri?,
        success: (Boolean) -> Unit,
        failed: (Boolean, Throwable) -> Unit
    ) {
        remoteHelper.uploadFirebaseData(data, imageUri, success, failed)
    }
}