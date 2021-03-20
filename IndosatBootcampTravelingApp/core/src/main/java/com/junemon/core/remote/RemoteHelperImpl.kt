package com.junemon.core.remote

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.junemon.core.di.dispatcher.DefaultDispatcher
import com.junemon.core.remote.util.PushFirebase
import com.junemon.core.remote.util.singleValueEvent
import com.junemon.core.remote.util.valueEventFlow
import com.junemon.model.data.PlaceRemoteEntity
import com.junemon.model.data.dto.mapToRemoteDomain
import com.junemon.model.domain.DataHelper
import com.junemon.model.domain.PlaceRemoteData
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

@ExperimentalCoroutinesApi
@FlowPreview
class RemoteHelperImpl @Inject constructor(
    private val storagePlaceReference: StorageReference,
    private val databasePlaceReference: DatabaseReference
) : RemoteHelper {

    /**observing operation*/
    override suspend fun getFirebaseData(): Flow<DataHelper<List<PlaceRemoteData>>> {
        return databasePlaceReference.valueEventFlow().map { value ->
            when(value){
                is PushFirebase.Changed ->{
                    val result = value.snapshot.children.mapNotNull {
                        it.getValue(PlaceRemoteEntity::class.java)
                    }.toList()
                    DataHelper.RemoteSourceValue(result.mapToRemoteDomain())
                }
                is PushFirebase.Cancelled->{
                    DataHelper.RemoteSourceError(value.error.toException())
                }
            }
        }.onStart { emit(DataHelper.RemoteSourceLoading) }
    }

    /**one-shot only operation*/
    override suspend fun getFirebaseOneShotData(): DataHelper<List<PlaceRemoteData>> {
        return when(val value = databasePlaceReference.singleValueEvent()){
            is PushFirebase.Changed -> {
                val result = value.snapshot.children.mapNotNull {
                    it.getValue(PlaceRemoteEntity::class.java)
                }.toList()

                DataHelper.RemoteSourceValue(result.mapToRemoteDomain())
            }
            is PushFirebase.Cancelled -> {
                DataHelper.RemoteSourceError(value.error.toException())
            }
        }
    }

    override fun uploadFirebaseData(
        data: PlaceRemoteData,
        imageUri: Uri?,
        success: (Boolean) -> Unit,
        failed: (Boolean, Throwable) -> Unit
    ) {
        if (imageUri != null) {
            val reference = storagePlaceReference.child(imageUri.lastPathSegment!!)
            reference.putFile(imageUri).apply {
                addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener {
                        data.placePicture = it.toString()
                        databasePlaceReference.push().setValue(data)
                    }
                }
                addOnCompleteListener { if (it.isSuccessful) success(true) }
                addOnFailureListener { failed(true, it) }
            }
        } else databasePlaceReference.push().setValue(data)
    }

    private fun <T> customSuccess(data: T): DataHelper<T> {
        return DataHelper.RemoteSourceValue(data)
    }
}