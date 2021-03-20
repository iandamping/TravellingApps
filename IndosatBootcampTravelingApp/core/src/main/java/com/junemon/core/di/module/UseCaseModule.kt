package com.junemon.core.di.module

import com.junemon.core.domain.usecase.PlaceUseCase
import com.junemon.core.domain.usecase.PlaceUseCaseImpl
import com.junemon.core.domain.usecase.ProfileUseCase
import com.junemon.core.domain.usecase.ProfileUseCaseImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface UseCaseModule {

    @Binds
    fun providePlaceUseCase(useCase: PlaceUseCaseImpl): PlaceUseCase

    @Binds
    fun provideProfileUseCase(useCase: ProfileUseCaseImpl): ProfileUseCase
}