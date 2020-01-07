package com.junemon.travellingapps.domain.di

import com.junemon.travellingapps.domain.repository.PlaceRepository
import com.junemon.travellingapps.domain.usecase.PlaceUseCase
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