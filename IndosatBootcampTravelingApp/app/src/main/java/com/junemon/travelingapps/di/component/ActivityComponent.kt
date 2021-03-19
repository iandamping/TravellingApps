package com.junemon.travelingapps.di.component

import com.junemon.travelingapps.activity.MainActivity
import com.junemon.travelingapps.activity.SplashActivity
import dagger.Subcomponent

@Subcomponent
interface ActivityComponent {
    fun inject(activity: SplashActivity)
    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }
}