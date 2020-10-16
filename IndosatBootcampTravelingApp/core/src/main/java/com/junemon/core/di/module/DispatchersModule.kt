package com.junemon.core.di.module

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Ian Damping on 24,July,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
val dispatchersModule = module {
    single(named("default")) { provideDefaultDispatcher() }
    single(named("main")) { provideMainDispatcher() }
    single(named("io")) { provideIoDispatcher() }
}

private fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
private fun provideMainDispatcher(): MainCoroutineDispatcher = Dispatchers.Main
private fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Main
