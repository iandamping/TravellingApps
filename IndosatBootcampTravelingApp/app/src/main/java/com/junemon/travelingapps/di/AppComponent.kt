package com.junemon.travelingapps.di

import android.app.Application
import com.junemon.core.cache.di.DatabaseHelperModule
import com.junemon.core.cache.di.DatabaseModule
import com.junemon.core.data.di.CoroutineModule
import com.junemon.core.data.di.DataModule
import com.junemon.core.di.module.CameraXModule
import com.junemon.core.di.module.GlideModule
import com.junemon.core.domain.di.DomainModule
import com.junemon.core.presentation.di.PresentationModule
import com.junemon.core.presentation.di.factory.ViewModelModule
import com.junemon.core.remote.di.RemoteHelperModule
import com.junemon.core.remote.di.RemoteModule
import com.junemon.travelingapps.PlaceApplication
import com.junemon.travelingapps.di.module.ActivityBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        CoroutineModule::class,
        DataModule::class,
        DomainModule::class,
        RemoteModule::class,
        DatabaseHelperModule::class,
        RemoteHelperModule::class,
        PresentationModule::class,
        GlideModule::class,
        CameraXModule::class]
)
interface AppComponent : AndroidInjector<PlaceApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}