package com.junemon.travelingapps.data.di

import com.junemon.cache.di.DatabaseHelperModule
import com.junemon.travelingapps.data.data.datasource.PlaceCacheDataSource
import com.junemon.travelingapps.data.data.datasource.PlaceRemoteDataSource
import com.junemon.travelingapps.data.data.repository.PlaceRepositoryImpl
import com.junemon.travelingapps.data.datasource.cache.PlaceCacheDataSourceImpl
import com.junemon.travelingapps.data.datasource.remote.PlaceRemoteDataSourceImpl
import com.junemon.travellingapps.domain.repository.PlaceRepository
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module(includes = [DatabaseHelperModule::class])
abstract class DataModule {

    @Binds
    abstract fun bindsPlaceRemoteDataSource(remoteDataSource: PlaceRemoteDataSourceImpl): PlaceRemoteDataSource

    @Binds
    abstract fun bindsPlaceCacheDataSource(cacheDataSource: PlaceCacheDataSourceImpl): PlaceCacheDataSource

    @Binds
    abstract fun bindsPlaceRepository(placeRepositoryImpl: PlaceRepositoryImpl): PlaceRepository
}