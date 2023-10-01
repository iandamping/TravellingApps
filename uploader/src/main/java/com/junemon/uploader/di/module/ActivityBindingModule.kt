package com.junemon.uploader.di.module

import com.junemon.core.di.scope.PerActivities
import com.junemon.uploader.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivities
    @ContributesAndroidInjector(
        modules = [  // fragments
            UploadModule::class]
    )
    abstract fun mainActivity(): MainActivity
}