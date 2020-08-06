package com.junemon.travelingapps.di.module

import com.junemon.core.di.scope.PerActivities
import com.junemon.travelingapps.activity.MainActivity
import com.junemon.travelingapps.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Ian Damping on 05,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class ActivityBindingModule {

    @PerActivities
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity


    @PerActivities
    @ContributesAndroidInjector(
        modules = [  // fragments
            PlaceModule::class]
    )
    abstract fun mainActivity(): MainActivity
    

}