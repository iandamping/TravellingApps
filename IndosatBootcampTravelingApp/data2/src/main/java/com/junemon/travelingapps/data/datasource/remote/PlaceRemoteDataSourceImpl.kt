package com.junemon.travelingapps.data.datasource.remote

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ian.app.helper.util.timberLogE
import com.junemon.travelingapps.data.BuildConfig.firebaseStorageUrl
import com.junemon.travelingapps.data.data.datasource.PlaceRemoteDataSource
import com.junemon.travelingapps.data.datasource.model.PlaceRemoteEntity
import com.junemon.travelingapps.data.datasource.model.mapToRemoteDomain
import com.junemon.travelingapps.data.util.Constant.placeNode
import com.junemon.travellingapps.domain.model.PlaceRemoteData
import kotlinx.coroutines.CompletableDeferred

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

    override suspend fun getFirebaseData(): List<PlaceRemoteData> {
        val results = CompletableDeferred<List<PlaceRemoteData>>()
        val container: MutableList<PlaceRemoteEntity> = mutableListOf()
        databasePlaceReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                timberLogE("Error happen ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    container.add(it.getValue(PlaceRemoteEntity::class.java)!!)
                }
                results.complete(container.mapToRemoteDomain())
            }
        })

        return results.await()
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
}