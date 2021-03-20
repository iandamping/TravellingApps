package com.junemon.core.di.module

import com.junemon.core.cache.util.classes.PlacesDaoHelperImpl
import com.junemon.core.cache.util.interfaces.PlacesDaoHelper
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface DatabaseHelperModule {

    @Binds
    fun bindsPlaceDaoHelper(placesDaoHelper: PlacesDaoHelperImpl): PlacesDaoHelper
}