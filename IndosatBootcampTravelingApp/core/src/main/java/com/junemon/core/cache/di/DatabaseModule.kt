package com.junemon.core.cache.di

import android.app.Application
import androidx.room.Room
import com.junemon.core.cache.db.PlaceDao
import com.junemon.core.cache.db.PlaceDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object DatabaseModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDb(app: Application): PlaceDatabase {
        return Room
            .databaseBuilder(app, PlaceDatabase::class.java, "wonderful_samarinda.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @JvmStatic
    fun provideGameDao(db: PlaceDatabase): PlaceDao {
        return db.placeDao()
    }
}