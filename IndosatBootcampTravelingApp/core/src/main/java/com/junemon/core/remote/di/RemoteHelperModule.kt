package com.junemon.core.remote.di

import com.junemon.core.remote.util.RemoteHelper
import com.junemon.core.remote.util.RemoteHelperImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class RemoteHelperModule {

    @Binds
    abstract fun bindsRemoteHelper(remoteHelper: RemoteHelperImpl): RemoteHelper
}