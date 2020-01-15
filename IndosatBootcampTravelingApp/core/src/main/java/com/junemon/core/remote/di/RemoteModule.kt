package com.junemon.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.junemon.core.BuildConfig.firebaseStorageUrl
import com.junemon.core.BuildConfig.placeNode
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object RemoteModule {

    @Provides
    @JvmStatic
    fun provideFirebaseDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child(placeNode)
    }

    @Provides
    @JvmStatic
    fun provideFirebaseStorage(): StorageReference {
        return FirebaseStorage.getInstance().getReferenceFromUrl(firebaseStorageUrl)
    }
}