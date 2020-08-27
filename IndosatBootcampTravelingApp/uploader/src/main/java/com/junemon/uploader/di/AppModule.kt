package com.junemon.uploader.di

import android.content.Context
import com.junemon.uploader.UploadApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: UploadApplication): Context {
        return application.applicationContext
    }
}