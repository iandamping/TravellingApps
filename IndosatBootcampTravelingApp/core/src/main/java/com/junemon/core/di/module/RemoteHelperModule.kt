package com.junemon.core.di.module

import com.junemon.core.remote.ProfileRemoteHelper
import com.junemon.core.remote.ProfileRemoteHelperImpl
import com.junemon.core.remote.RemoteHelper
import com.junemon.core.remote.RemoteHelperImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface RemoteHelperModule {

    @Binds
    fun bindsRemoteHelper(remoteHelper: RemoteHelperImpl): RemoteHelper


    @Binds
    fun bindsProfileRemoteHelper(remoteHelper: ProfileRemoteHelperImpl): ProfileRemoteHelper
}