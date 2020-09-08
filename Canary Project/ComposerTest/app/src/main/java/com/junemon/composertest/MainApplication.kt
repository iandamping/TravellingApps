package com.junemon.composertest

import android.app.Application
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import com.junemon.gamesapi.di.injectData
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin {
            androidContext(this@MainApplication)
            injectData()
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}