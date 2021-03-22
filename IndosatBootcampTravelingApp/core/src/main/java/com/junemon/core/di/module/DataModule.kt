package com.junemon.core.di.module

import com.junemon.core.data.data.datasource.PlaceCacheDataSource
import com.junemon.core.data.data.datasource.PlaceRemoteDataSource
import com.junemon.core.data.data.datasource.ProfileRemoteDataSource
import com.junemon.core.data.data.repository.PlaceRepositoryImpl
import com.junemon.core.data.data.repository.ProfileRepositoryImpl
import com.junemon.core.data.datasource.cache.PlaceCacheDataSourceImpl
import com.junemon.core.data.datasource.remote.PlaceRemoteDataSourceImpl
import com.junemon.core.data.datasource.remote.ProfileRemoteDataSourceImpl
import com.junemon.core.domain.repository.PlaceRepository
import com.junemon.core.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface DataModule {

    @Binds
    fun bindsPlaceRemoteDataSource(remoteDataSource: PlaceRemoteDataSourceImpl): PlaceRemoteDataSource

    @Binds
    fun bindsProfileRemoteDataSource(remoteDataSource: ProfileRemoteDataSourceImpl): ProfileRemoteDataSource

    @Binds
    fun bindsPlaceCacheDataSource(cacheDataSource: PlaceCacheDataSourceImpl): PlaceCacheDataSource

    @Binds
    fun bindsPlaceRepository(placeRepositoryImpl: PlaceRepositoryImpl): PlaceRepository

    @Binds
    fun bindsProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}