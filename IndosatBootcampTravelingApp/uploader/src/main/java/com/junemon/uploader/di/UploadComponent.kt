package com.junemon.uploader.di

import com.junemon.core.di.module.DatabaseHelperModule
import com.junemon.core.di.module.DatabaseModule
import com.junemon.core.di.module.CoroutineModule
import com.junemon.core.di.module.DataModule
import com.junemon.core.di.module.CameraXModule
import com.junemon.core.di.module.GlideModule
import com.junemon.core.di.module.UseCaseModule
import com.junemon.core.di.module.ViewModelModule
import com.junemon.core.di.module.RemoteHelperModule
import com.junemon.core.di.module.RemoteModule
import com.junemon.uploader.UploadApplication
import com.junemon.uploader.di.module.ActivityBindingModule
import com.junemon.uploader.di.module.AppModule
import com.junemon.uploader.di.module.PresentationModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Ian Damping on 19,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        CoroutineModule::class,
        DataModule::class,
        UseCaseModule::class,
        RemoteModule::class,
        DatabaseHelperModule::class,
        RemoteHelperModule::class,
        PresentationModule::class,
        GlideModule::class,
        CameraXModule::class]
)
interface UploadComponent : AndroidInjector<UploadApplication> {

    /*@Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): UploadComponent
    }*/

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<UploadApplication>()
}