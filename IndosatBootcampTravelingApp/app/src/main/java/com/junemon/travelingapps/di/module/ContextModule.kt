package com.junemon.travelingapps.di.module

import android.content.Context
import com.junemon.travelingapps.PlaceApplication
import dagger.Module
import dagger.Provides

@Module
class ContextModule {

    @Provides
    fun provideContext(application: PlaceApplication): Context {
        return application.applicationContext
    }
}
