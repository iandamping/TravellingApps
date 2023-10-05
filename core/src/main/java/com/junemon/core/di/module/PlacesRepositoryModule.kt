package com.junemon.core.di.module

import com.junemon.core.domain.repository.PlacesRepository
import com.junemon.core.domain.repository.PlacesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PlacesRepositoryModule {

    @Binds
    fun bindsPlacesRepository(impl: PlacesRepositoryImpl): PlacesRepository
}