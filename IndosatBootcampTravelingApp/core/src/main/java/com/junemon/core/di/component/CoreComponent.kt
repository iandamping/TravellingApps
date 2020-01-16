package com.junemon.core.di.component

import android.app.Application
import com.junemon.core.cache.util.interfaces.PlacesDaoHelper
import com.junemon.core.data.data.datasource.PlaceCacheDataSource
import com.junemon.core.data.data.datasource.PlaceRemoteDataSource
import com.junemon.core.data.di.CoroutineModule
import com.junemon.core.data.di.DataModule
import com.junemon.core.domain.di.DomainModule
import com.junemon.core.domain.repository.PlaceRepository
import com.junemon.core.presentation.di.PresentationModule
import com.junemon.core.presentation.util.interfaces.CommonHelper
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.PermissionHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.core.remote.util.RemoteHelper
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ian Damping on 16,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

@Component(
    modules = [CoroutineModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class]
)
@Singleton
interface CoreComponent {

    fun provideLoadImageHelper(): LoadImageHelper

    fun provideIntentUtil(): IntentHelper

    fun provideImageUtil(): ImageHelper

    fun providePermissionHelperUtil(): PermissionHelper

    fun provideRecyclerViewHelper(): RecyclerHelper

    fun provideViewHelper(): ViewHelper

    fun provideCommmonHelper(): CommonHelper

    fun providePlaceRepository(): PlaceRepository

    fun providePlaceRemoteDataSource(): PlaceRemoteDataSource

    fun providePlaceCacheDataSource(): PlaceCacheDataSource

    fun providePlacesDaoHelper(): PlacesDaoHelper

    fun provideRemoteHelper(): RemoteHelper

    @Component.Factory
    interface Factory {
        fun injectApplication(@BindsInstance application: Application): CoreComponent
    }
}