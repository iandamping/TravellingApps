package com.junemon.travelingapps.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.junemon.travelingapps.data.datasource.model.PlaceDbEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Dao
interface PlaceDao {
    @Query("SELECT * FROM place_table")
    fun loadAllPlace(): Flow<List<PlaceDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlace(vararg tagsData: PlaceDbEntity)

    @Query("DELETE FROM place_table")
    suspend fun deleteAllPlace()
}