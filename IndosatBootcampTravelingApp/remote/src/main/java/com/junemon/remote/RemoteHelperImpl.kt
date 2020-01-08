package com.junemon.remote

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.junemon.model.data.PlaceRemoteEntity
import com.junemon.model.data.dto.mapToRemoteDomain
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class RemoteHelperImpl @Inject constructor(
    private val storagePlaceReference: StorageReference,
    private val databasePlaceReference: DatabaseReference
) : RemoteHelper {

    override suspend fun getFirebaseData(): Results<List<PlaceRemoteData>> {
        val container: MutableList<PlaceRemoteEntity> = mutableListOf()
        return suspendCancellableCoroutine { cancellableContinuation ->
            databasePlaceReference.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    cancellableContinuation.resume(customError(p0.toException()))
                }

                override fun onDataChange(p0: DataSnapshot) {

                    p0.children.forEach {
                        container.add(it.getValue(PlaceRemoteEntity::class.java)!!)
                    }

                    cancellableContinuation.resume(Results.Success(container.mapToRemoteDomain()))
                }
            })
            cancellableContinuation.invokeOnCancellation {
                cancellableContinuation.resume(customError(Exception(it)))
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun getFlowFirebaseData(): Flow<Results<List<PlaceRemoteData>>> {
        val container: MutableList<PlaceRemoteEntity> = mutableListOf()
        return callbackFlow {
            offer(Results.Loading)
            databasePlaceReference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    close(p0.toException())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        container.add(it.getValue(PlaceRemoteEntity::class.java)!!)
                    }
                    if (!this@callbackFlow.channel.isClosedForSend){
                        offer(Results.Success(container.mapToRemoteDomain()))
                    }
                }
            })
            awaitClose { cancel() }
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
        } else  databasePlaceReference.push().setValue(data)
    }

    private fun <T> customError(exception: Exception): Results<T> {
        return Results.Error(exception)
    }
}