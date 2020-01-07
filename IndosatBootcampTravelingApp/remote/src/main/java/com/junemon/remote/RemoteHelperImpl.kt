package com.junemon.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class RemoteHelperImpl @Inject constructor(
    private val storagePlaceReference: StorageReference,
    private val databasePlaceReference: DatabaseReference
) : RemoteHelper {
    override fun getFirebaseStorageReference(): StorageReference {
        return storagePlaceReference
    }

    override fun getFirebaseDatabaseReference(): DatabaseReference {
        return databasePlaceReference
    }
}