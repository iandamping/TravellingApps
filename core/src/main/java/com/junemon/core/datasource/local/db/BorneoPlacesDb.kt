package com.junemon.core.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.junemon.core.datasource.local.entity.BorneoLocalPlacesEntity
import com.junemon.core.datasource.local.entity.FavoriteBorneoLocalPlacesEntity

@Database(
    entities = [BorneoLocalPlacesEntity::class, FavoriteBorneoLocalPlacesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BorneoPlacesDb : RoomDatabase() {
    abstract fun placeDao(): BorneoPlacesDao

    abstract fun favoritePlaceDao(): FavoriteBorneoPlacesDao
}