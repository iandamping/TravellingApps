package com.junemon.uploader.di.module

import androidx.lifecycle.ViewModel
import com.junemon.core.di.scope.FeatureScope
import com.junemon.core.di.factory.ViewModelKey
import com.junemon.uploader.feature.camera.OpenCameraFragment
import com.junemon.uploader.feature.camera.SelectImageFragment
import com.junemon.uploader.feature.upload.UploadFragment
import com.junemon.uploader.vm.UploadViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 19,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class UploadModule {

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeUploadFragment(): UploadFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeOpenCameraFragment(): OpenCameraFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeOpenSelectImageFragment(): SelectImageFragment

    @Binds
    @IntoMap
    @ViewModelKey(UploadViewModel::class)
    abstract fun bindUploadViewModel(uploadViewModel: UploadViewModel): ViewModel
}