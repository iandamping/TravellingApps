package com.junemon.core.di.module

import android.content.Context
import androidx.room.Room
import com.junemon.core.datasource.local.db.BorneoPlacesDao
import com.junemon.core.datasource.local.db.BorneoPlacesDb
import com.junemon.core.datasource.local.db.FavoriteBorneoPlacesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "places.db"

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): BorneoPlacesDb {
        return Room
            .databaseBuilder(context, BorneoPlacesDb::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBorneoPlacesDao(db: BorneoPlacesDb): BorneoPlacesDao {
        return db.placeDao()
    }

    @Provides
    fun provideFavoriteBorneoPlacesDao(db: BorneoPlacesDb): FavoriteBorneoPlacesDao {
        return db.favoritePlaceDao()
    }

}