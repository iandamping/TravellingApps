package com.junemon.core.di.module

import com.junemon.core.datasource.remote.PlacesRemoteDataSource
import com.junemon.core.datasource.remote.PlacesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PlacesRemoteDataSourceModule {

    @Binds
    fun bindsPlacesRemoteDataSource(impl: PlacesRemoteDataSourceImpl): PlacesRemoteDataSource

}