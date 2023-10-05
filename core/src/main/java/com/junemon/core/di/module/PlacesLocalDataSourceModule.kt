package com.junemon.core.di.module

import com.ian.junemon.holidaytoborneo.core.datasource.local.PlacesLocalDataSource
import com.junemon.core.datasource.local.PlacesLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PlacesLocalDataSourceModule {

    @Binds
    fun bindsPlacesLocalDataSource(impl: PlacesLocalDataSourceImpl): PlacesLocalDataSource
}