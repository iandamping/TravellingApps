package com.junemon.travelingapps.di

import com.junemon.core.di.component.CoreComponent
import com.junemon.core.di.scope.ApplicationScope
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

    fun inject(activity: SplashActivity)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun coreComponent(coreComponent: CoreComponent): AppComponent
    }
}