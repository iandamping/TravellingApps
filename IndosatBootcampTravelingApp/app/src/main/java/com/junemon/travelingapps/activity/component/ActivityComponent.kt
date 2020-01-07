package com.junemon.travelingapps.activity.component

import com.junemon.daggerinyourface.di.scope.PerActivities
import com.junemon.travelingapps.activity.MainActivity
import com.junemon.travelingapps.activity.SplashActivity
import com.junemon.travelingapps.di.component.FragmentSubComponent
import com.junemon.travelingapps.feature.di.FeatureComponent
import dagger.Subcomponent

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@PerActivities
@Subcomponent(modules = [FragmentSubComponent::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: SplashActivity)

    fun getFeatureComponent(): FeatureComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }
}