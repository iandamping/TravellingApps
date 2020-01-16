package com.junemon.core.domain.di

import com.junemon.core.domain.repository.PlaceRepository
import com.junemon.core.domain.usecase.PlaceUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object DomainModule {

    @Provides
    @JvmStatic
    fun providePlaceUseCase(repository: PlaceRepository): PlaceUseCase {
        return PlaceUseCase(repository)
    }
}