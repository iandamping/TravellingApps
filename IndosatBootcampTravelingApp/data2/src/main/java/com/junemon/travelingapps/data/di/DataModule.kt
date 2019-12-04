package com.junemon.travelingapps.data.di

import androidx.room.Room
import com.junemon.travelingapps.data.data.datasource.PlaceCacheDataSource
import com.junemon.travelingapps.data.data.datasource.PlaceRemoteDataSource
import com.junemon.travelingapps.data.data.repository.PlaceRepositoryImpl
import com.junemon.travelingapps.data.datasource.cache.PlaceCacheDataSourceImpl
import com.junemon.travelingapps.data.datasource.remote.PlaceRemoteDataSourceImpl
import com.junemon.travelingapps.data.db.PlaceDatabase
import com.junemon.travellingapps.domain.repository.PlaceRepository
import org.koin.dsl.module

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), PlaceDatabase::class.java, "Place Database")
            .fallbackToDestructiveMigration().build()
    }
    single { get<PlaceDatabase>().placeDao() }
}

val dataSourceModule = module {
    single { PlaceCacheDataSourceImpl(get()) as PlaceCacheDataSource }
    single { PlaceRemoteDataSourceImpl() as PlaceRemoteDataSource }
}

val repositoryModules = module {
    single {
        PlaceRepositoryImpl(
            remoteDataSource = get(),
            cacheDataSource = get()
        ) as PlaceRepository
    }
}