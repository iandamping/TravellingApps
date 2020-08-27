package com.junemon.uploader

import com.junemon.uploader.di.DaggerUploadComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Created by Ian Damping on 19,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerUploadComponent.builder().create(this)
    }
}