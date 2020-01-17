package com.junemon.travelingapps

import android.app.Activity
import android.app.Application
import com.junemon.core.di.component.CoreComponent
import com.junemon.core.di.component.DaggerCoreComponent
import com.junemon.travelingapps.di.AppComponent
import com.junemon.travelingapps.di.DaggerAppComponent
import timber.log.Timber

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().injectApplication(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the application that will be used as Context in the graph
        return DaggerAppComponent.factory().coreComponent(coreComponent)
    }
}

fun Activity.appComponent() = (application as PlaceApplication).appComponent

