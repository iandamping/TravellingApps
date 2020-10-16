package com.junemon.uploader

import android.app.Application
import com.junemon.uploader.di.module.injectData
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Ian Damping on 19,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@UploadApplication)
            injectData()
        }
    }

}