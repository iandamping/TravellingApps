package com.junemon.cache.di

import com.junemon.cache.util.classes.PlacesDaoHelperImpl
import com.junemon.cache.util.interfaces.PlacesDaoHelper
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class DatabaseHelperModule {

    @Binds
    abstract fun bindsPlaceDaoHelper(placesDaoHelper: PlacesDaoHelperImpl): PlacesDaoHelper
}