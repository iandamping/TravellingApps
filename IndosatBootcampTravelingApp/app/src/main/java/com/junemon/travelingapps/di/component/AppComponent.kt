package com.junemon.travelingapps.di.component

import android.app.Application
import com.junemon.cache.di.DatabaseModule
import com.junemon.daggerinyourface.di.scope.ApplicationScope
import com.junemon.remote.RemoteModule
import com.junemon.travelingapps.activity.component.ActivityComponent
import com.junemon.travelingapps.data.di.CoroutineModule
import com.junemon.travelingapps.data.di.DataModule
import com.junemon.travelingapps.presentation.di.PresentationModule
import com.junemon.travellingapps.domain.di.DomainModule
import dagger.BindsInstance
import dagger.Component

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ApplicationScope
@Component(
    modules = [
        CoroutineModule::class,
        DatabaseModule::class,
        RemoteModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        AppSubComponent::class]
)
interface AppComponent {

    fun getActivityComponent(): ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun injectApplication(@BindsInstance application: Application): AppComponent
    }
}