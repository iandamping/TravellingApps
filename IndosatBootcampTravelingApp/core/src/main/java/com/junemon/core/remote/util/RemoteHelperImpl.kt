package com.junemon.core.remote.util

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.junemon.core.data.di.DefaultDispatcher
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
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val storagePlaceReference: StorageReference,
    private val databasePlaceReference: DatabaseReference
) : RemoteHelper {

    private val channel = ConflatedBroadcastChannel<DataHelper<List<PlaceRemoteData>>>()

    /**observing operation*/
    override fun getFirebaseData(): Flow<DataHelper<List<PlaceRemoteData>>> {
        val container: MutableSet<PlaceRemoteEntity> = mutableSetOf()
        databasePlaceReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                if (!channel.isClosedForSend) {
                    channel.offer(DataHelper.RemoteSourceError(p0.toException()))
                }
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    container.add(it.getValue(PlaceRemoteEntity::class.java)!!)
                }
                if (!channel.isClosedForSend) {
                    channel.offer(customSuccess(container.toList().mapToRemoteDomain()))
                }
            }
        })
        return channel.asFlow()
    }

    /**one-shot only operation*/
    override suspend fun getFirebaseOneShotData(): DataHelper<List<PlaceRemoteData>> {
        val result: CompletableDeferred<DataHelper<List<PlaceRemoteData>>> = CompletableDeferred()
        val container: MutableSet<PlaceRemoteEntity> = mutableSetOf()
        withContext(defaultDispatcher) {
            databasePlaceReference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    result.complete(DataHelper.RemoteSourceError(p0.toException()))
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        container.add(it.getValue(PlaceRemoteEntity::class.java)!!)
                    }
                    result.complete(customSuccess(container.toList().mapToRemoteDomain()))
                }
            })
        }
        return result.await()
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