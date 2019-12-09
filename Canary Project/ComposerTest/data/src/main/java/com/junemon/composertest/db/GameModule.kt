package com.junemon.gamesapi.data.db

import androidx.room.Room
import org.koin.dsl.module

/**
 * Created by Ian Damping on 31,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
val databaseModule = module {
    single {
        Room.databaseBuilder(get(), GameDatabase::class.java, "Game Database")
            .fallbackToDestructiveMigration().build()
    }
    single { get<GameDatabase>().gameDao() }
    single { get<GameDatabase>().publisherDao() }
}