package com.junemon.core.di.module

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.junemon.core.BuildConfig.placeNode
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    @Provides
    fun provideFirebaseDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child(placeNode)
    }


}