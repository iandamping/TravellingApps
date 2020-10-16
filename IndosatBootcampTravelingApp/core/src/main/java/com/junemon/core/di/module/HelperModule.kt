package com.junemon.core.di.module

import com.junemon.core.cache.util.classes.PlacesDaoHelperImpl
import com.junemon.core.cache.util.interfaces.PlacesDaoHelper
import com.junemon.core.remote.util.ProfileRemoteHelper
import com.junemon.core.remote.util.ProfileRemoteHelperImpl
import com.junemon.core.remote.util.RemoteHelper
import com.junemon.core.remote.util.RemoteHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
val remoteHelperModule = module {
    single {
        ProfileRemoteHelperImpl(
            ioDispatcher = get(named("io")), context = androidContext(),
            mFirebaseAuth = get()
        ) as ProfileRemoteHelper
    }

    single {
        RemoteHelperImpl(
            defaultDispatcher = get(named("default")),
            storagePlaceReference = get(),
            databasePlaceReference = get()
        ) as RemoteHelper
    }
}

val databaseHelperModule = module {
    single { PlacesDaoHelperImpl(get()) as PlacesDaoHelper }
}