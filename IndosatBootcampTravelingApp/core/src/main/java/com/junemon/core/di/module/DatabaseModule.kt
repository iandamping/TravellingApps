package com.junemon.core.di.module

import androidx.room.Room
import com.junemon.core.cache.db.PlaceDatabase
import com.junemon.core.cache.util.classes.PlacesDaoHelperImpl
import com.junemon.core.cache.util.interfaces.PlacesDaoHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), PlaceDatabase::class.java, "wonderful_samarinda.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<PlaceDatabase>().placeDao() }
}


