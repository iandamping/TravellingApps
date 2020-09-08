package com.junemon.gamesapi.di

import com.ian.app.helper.di.baseResultModule
import com.ian.app.helper.di.commonHelperModule
import com.ian.app.helper.di.helperNetworkModule
import com.ian.recyclerviewhelper.di.baseRecyclerViewModule
import com.junemon.gamesapi.data.data.datasource.GameCacheDataSource
import com.junemon.gamesapi.data.data.datasource.GamesRemoteDataSource
import com.junemon.gamesapi.data.data.datasource.PublisherCacheDataSource
import com.junemon.gamesapi.data.data.datasource.PublisherRemoteDataSource
import com.junemon.gamesapi.data.data.repository.GamesRepositoryImpl
import com.junemon.gamesapi.data.data.repository.PublisherRepositoryImpl
import com.junemon.gamesapi.data.datasource.cache.GameCacheDataSourceImpl
import com.junemon.gamesapi.data.datasource.cache.PublisherCaceDataSourceImpl
import com.junemon.gamesapi.data.datasource.remote.ApiConstant
import com.junemon.gamesapi.data.datasource.remote.GamesApi
import com.junemon.gamesapi.data.datasource.remote.GamesRemoteDataSourceImpl
import com.junemon.gamesapi.data.datasource.remote.PublishersRemoteDataSourceImpl
import com.junemon.gamesapi.data.db.databaseModule
import com.junemon.gamesapi.domain2.repository.GamesRepository
import com.junemon.gamesapi.domain2.repository.PublisherRepository
import com.junemon.gamesapi.domain2.usecase.GamesUseCase
import com.junemon.gamesapi.domain2.usecase.PublisherUseCase
import com.junemon.gamesapi.ui.GameDataViewModel
import com.junemon.gamesapi.ui.PublisherDataViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Created by Ian Damping on 29,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
fun injectData() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            networkModule,
            dataSourceModule,
            repositoryModule,
            useCaseModule,
            viewmodelModule,
            baseRecyclerViewModule,
            baseResultModule,
            commonHelperModule,
            databaseModule
        )
    )
}

private val viewmodelModule = module {
    viewModel { GameDataViewModel(get()) }
    viewModel { PublisherDataViewModel(get()) }
}

private val useCaseModule = module {
    factory { GamesUseCase(get()) }
    factory { PublisherUseCase(get()) }
}

private val networkModule = helperNetworkModule<GamesApi>(ApiConstant.baseUrl)

private val dataSourceModule = module {
    single { GamesRemoteDataSourceImpl(get()) as GamesRemoteDataSource }
    single { GameCacheDataSourceImpl(get()) as GameCacheDataSource }
    single { PublisherCaceDataSourceImpl(get()) as PublisherCacheDataSource }
    single { PublishersRemoteDataSourceImpl(get()) as PublisherRemoteDataSource }
}

private val repositoryModule = module {
    single {
        GamesRepositoryImpl(
            remoteSource = get(),
            localSource = get(),
            cachingResult = get()
        ) as GamesRepository
    }
    single {
        PublisherRepositoryImpl(
            remoteDataSource = get(),
            cacheDataSource = get()
        ) as PublisherRepository
    }
}


