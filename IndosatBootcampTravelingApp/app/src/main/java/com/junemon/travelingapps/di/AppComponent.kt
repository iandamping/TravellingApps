package com.junemon.travelingapps.di

import com.junemon.core.cache.util.interfaces.PlacesDaoHelper
import com.junemon.core.data.data.datasource.PlaceCacheDataSource
import com.junemon.core.data.data.datasource.PlaceRemoteDataSource
import com.junemon.core.di.component.CoreComponent
import com.junemon.core.di.scope.ApplicationScope
import com.junemon.core.domain.repository.PlaceRepository
import com.junemon.core.presentation.util.interfaces.CommonHelper
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.PermissionHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.core.remote.util.RemoteHelper
import com.junemon.travelingapps.activity.MainActivity
import com.junemon.travelingapps.activity.SplashActivity
import dagger.Component

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ApplicationScope
@Component(dependencies = [CoreComponent::class])
interface AppComponent {

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
        fun coreComponent(coreComponent: CoreComponent): AppComponent
    }
}