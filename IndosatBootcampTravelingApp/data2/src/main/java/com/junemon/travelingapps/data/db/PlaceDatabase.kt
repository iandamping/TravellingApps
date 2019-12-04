package com.junemon.travelingapps.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.junemon.travelingapps.data.datasource.model.PlaceDbEntity

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Database(entities = [PlaceDbEntity::class], version = 1, exportSchema = false)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}