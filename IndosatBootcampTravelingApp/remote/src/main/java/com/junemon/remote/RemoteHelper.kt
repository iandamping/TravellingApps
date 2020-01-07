package com.junemon.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface RemoteHelper {

    fun getFirebaseStorageReference(): StorageReference

    fun getFirebaseDatabaseReference(): DatabaseReference
}