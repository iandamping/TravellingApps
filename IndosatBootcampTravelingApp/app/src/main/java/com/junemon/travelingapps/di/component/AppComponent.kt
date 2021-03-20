package com.junemon.travelingapps.di.component

import com.junemon.core.di.module.ViewModelModule
import com.junemon.core.di.module.CoroutineModule
import com.junemon.core.di.module.CoroutineScopeModule
import com.junemon.core.di.module.DataModule
import com.junemon.core.di.module.DatabaseHelperModule
import com.junemon.core.di.module.DatabaseModule
import com.junemon.core.di.module.GlideModule
import com.junemon.core.di.module.UseCaseModule
import com.junemon.core.di.module.RemoteHelperModule
import com.junemon.core.di.module.RemoteModule
import com.junemon.travelingapps.PlaceApplication
import com.junemon.travelingapps.di.module.ActivitySubModule
import com.junemon.travelingapps.di.module.AppViewModelModule
import com.junemon.travelingapps.di.module.ContextModule
import com.junemon.travelingapps.di.module.FragmentSubModule
import com.junemon.travelingapps.di.module.PresentationModule
import com.junemon.travelingapps.feature.home.HomeComponent
import com.junemon.travelingapps.feature.pagination.PaginationComponent
import com.junemon.travelingapps.feature.search.SearchComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Singleton
@Component(
    modules = [
        ContextModule::class,
        ActivitySubModule::class,
        FragmentSubModule::class,
        ViewModelModule::class,
        AppViewModelModule::class,
        DatabaseModule::class,
        CoroutineModule::class,
        DataModule::class,
        UseCaseModule::class,
        RemoteModule::class,
        DatabaseHelperModule::class,
        RemoteHelperModule::class,
        PresentationModule::class,
        CoroutineScopeModule::class,
        GlideModule::class]
)
interface AppComponent {

    fun getActivityComponent(): ActivityComponent.Factory

    fun getPaginationComponent(): PaginationComponent.Factory

    fun getSearchComponent(): SearchComponent.Factory

    fun getHomeComponent(): HomeComponent.Factory

    fun getFragmentComponent():FragmentComponent.Factory

    @Component.Factory
    interface Factory {
        fun injectApplication(@BindsInstance application: PlaceApplication): AppComponent
    }
}


interface AppComponentProvider {

    fun provideApplicationComponent(): AppComponent
}