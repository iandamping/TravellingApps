package com.junemon.travelingapps.di.component

import com.junemon.core.di.component.CoreComponent
import com.junemon.travelingapps.activity.MainActivity
import com.junemon.travelingapps.activity.SplashActivity
import com.junemon.travelingapps.di.scope.ApplicationScope
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