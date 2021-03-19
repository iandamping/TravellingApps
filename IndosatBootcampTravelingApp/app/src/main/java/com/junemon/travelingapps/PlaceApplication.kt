package com.junemon.travelingapps

import android.app.Application
import com.junemon.travelingapps.di.component.AppComponent
import com.junemon.travelingapps.di.component.AppComponentProvider
import com.junemon.travelingapps.di.component.DaggerAppComponent
import timber.log.Timber

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceApplication : Application(), AppComponentProvider {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun provideApplicationComponent(): AppComponent {
        return DaggerAppComponent.factory().injectApplication(this)
    }
}


