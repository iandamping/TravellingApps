package com.junemon.core.di.module

import com.junemon.core.di.dispatcher.DefaultDispatcher
import com.junemon.core.di.dispatcher.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

/**
 * Created by Ian Damping on 25,February,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object CoroutineScopeModule {

    @Provides
    @Singleton
    fun providesDefaultApplicationScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

}