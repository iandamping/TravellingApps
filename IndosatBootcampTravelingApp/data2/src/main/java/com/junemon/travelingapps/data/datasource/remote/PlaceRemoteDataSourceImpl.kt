package com.junemon.travelingapps.data.datasource.remote

import android.net.Uri
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ian.app.helper.data.ResultToConsume
import com.ian.app.helper.util.logE
import com.ian.app.helper.util.timberLogE
import com.junemon.travelingapps.data.BuildConfig.firebaseStorageUrl
import com.junemon.travelingapps.data.data.datasource.PlaceRemoteDataSource
import com.junemon.travelingapps.data.datasource.model.PlaceRemoteEntity
import com.junemon.travelingapps.data.datasource.model.mapToRemoteDomain
import com.junemon.travelingapps.data.util.Constant.placeNode
import com.junemon.travellingapps.domain.model.PlaceRemoteData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceRemoteDataSourceImpl : PlaceRemoteDataSource {
    private val mFirebaseDatabase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val mFirebaseStorage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    private val storagePlaceReference: StorageReference by lazy {
        mFirebaseStorage.getReferenceFromUrl(
            firebaseStorageUrl
        )
    }
    private val databasePlaceReference: DatabaseReference by lazy {
        mFirebaseDatabase.reference.child(
            placeNode
        )
    }

    @ExperimentalCoroutinesApi
    override suspend fun getFirebaseData(): ResultToConsume<List<PlaceRemoteData>> {
        val container: MutableList<PlaceRemoteEntity> = mutableListOf()
        return suspendCancellableCoroutine { cancellableContinuation ->
            databasePlaceReference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    cancellableContinuation.resume(error(" ${p0.code} ${p0.message}"))
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        container.add(it.getValue(PlaceRemoteEntity::class.java)!!)
                    }
                    cancellableContinuation.resume(ResultToConsume.success(container.mapToRemoteDomain()))
                }
            })
            cancellableContinuation.invokeOnCancellation {
                cancellableContinuation.resume(error(it?.message ?: it.toString()))
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun getFlowFirebaseData(): Flow<ResultToConsume<List<PlaceRemoteData>>> {
        val container: MutableList<PlaceRemoteEntity> = mutableListOf()
        return callbackFlow {
            databasePlaceReference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    close(p0.toException())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        container.add(it.getValue(PlaceRemoteEntity::class.java)!!)
                    }
                    offer(ResultToConsume.success(container.mapToRemoteDomain()))
                }
            })
            awaitClose { cancel() }
        }
    }

    override fun setFirebaseData(data: PlaceRemoteData, imageUri: Uri?, success: (Boolean) -> Unit, failed: (Boolean, Throwable) -> Unit) {
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


    private fun <T> error(message: String): ResultToConsume<T> {
        return ResultToConsume.error("Network call has failed for a following reason: $message")
    }
}