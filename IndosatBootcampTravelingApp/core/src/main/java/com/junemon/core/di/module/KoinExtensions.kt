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
import com.junemon.core.domain.usecase.PlaceUseCase
import com.junemon.core.domain.usecase.ProfileUseCase
import org.koin.dsl.ScopeDSL

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

private fun ScopeDSL.bindsPlaceRemoteDataSource() =
    scoped {
        PlaceRemoteDataSourceImpl(
            remoteHelper = get()
        ) as PlaceRemoteDataSource
    }

private fun ScopeDSL.bindsPlaceCacheDataSource() =
    scoped {
        PlaceCacheDataSourceImpl(
            placeDao = get()
        ) as PlaceCacheDataSource
    }

private fun ScopeDSL.bindsProfileRemoteDataSource() =
    scoped {
        ProfileRemoteDataSourceImpl(
            profileRemoteHelper = get()
        ) as ProfileRemoteDataSource
    }

private fun ScopeDSL.bindsPlaceRepository() =
    scoped {
        PlaceRepositoryImpl(
            remoteDataSource = get(),
            cacheDataSource = get()
        ) as PlaceRepository
    }

private fun ScopeDSL.bindsProfileRepository() =
    scoped {
        ProfileRepositoryImpl(
            remoteDataSource = get()
        ) as ProfileRepository
    }

private fun ScopeDSL.providePlaceUseCase() =
    scoped {
        PlaceUseCase(
          repository = get()
        )
    }

private fun ScopeDSL.provideProfileUseCase() =
    scoped {
        ProfileUseCase(
            repository = get()
        )
    }

fun ScopeDSL.placeInjector() {
    bindsPlaceRemoteDataSource()
    bindsPlaceCacheDataSource()
    bindsPlaceRepository()
    providePlaceUseCase()
}

fun ScopeDSL.profileInjector() {
    bindsProfileRemoteDataSource()
    bindsProfileRepository()
    provideProfileUseCase()
}

