package com.junemon.uploader

import com.junemon.uploader.di.DaggerUploadComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by Ian Damping on 19,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerUploadComponent.builder().application(this).build()
    }
}