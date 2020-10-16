package com.junemon.core.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.junemon.core.BuildConfig
import org.koin.dsl.module

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
val remoteModule = module{
    single { provideFirebaseDatabase() }
    single { provideFirebaseStorage() }
    single { provideFirebaseAuth() }

}

private fun provideFirebaseDatabase(): DatabaseReference {
    return FirebaseDatabase.getInstance().reference.child(BuildConfig.placeNode)
}
private fun provideFirebaseStorage(): StorageReference {
    return FirebaseStorage.getInstance().getReferenceFromUrl(BuildConfig.firebaseStorageUrl)
}
private fun provideFirebaseAuth(): FirebaseAuth {
    return FirebaseAuth.getInstance()
}