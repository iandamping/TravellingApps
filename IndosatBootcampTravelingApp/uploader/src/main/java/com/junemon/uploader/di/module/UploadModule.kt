package com.junemon.uploader.di.module

import com.junemon.core.di.module.cameraXModule
import com.junemon.core.di.module.databaseHelperModule
import com.junemon.core.di.module.databaseModule
import com.junemon.core.di.module.dispatchersModule
import com.junemon.core.di.module.glideModule
import com.junemon.core.di.module.presentationModule
import com.junemon.core.di.module.remoteHelperModule
import com.junemon.core.di.module.remoteModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.context.loadKoinModules

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
fun injectData() = loadFeature

@ExperimentalCoroutinesApi
private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            cameraXModule,
            databaseModule,
            databaseHelperModule,
            remoteModule,
            remoteHelperModule,
            glideModule,
            presentationModule,
            fragmentModule,
            dispatchersModule
        )
    )
}
