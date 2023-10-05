package com.junemon.core.di.module

import com.junemon.core.domain.usecase.PlaceUseCase
import com.junemon.core.domain.usecase.PlaceUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PlaceUseCaseImplModule {

    @Binds
    fun bindsPlaceUseCase(impl: PlaceUseCaseImpl): PlaceUseCase
}