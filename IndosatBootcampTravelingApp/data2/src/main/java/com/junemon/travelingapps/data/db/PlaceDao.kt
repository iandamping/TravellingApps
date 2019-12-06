package com.junemon.travelingapps.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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

    @Query("SELECT * FROM place_table WHERE place_type = :placeType")
    fun loadAllBalanceByMonth(placeType: String): Flow<List<PlaceDbEntity>>

    @Insert
    suspend fun insertPlace(vararg tagsData: PlaceDbEntity)

    @Query("DELETE FROM place_table")
    suspend fun deleteAllPlace()

    @Transaction
    suspend fun insertAllPlace(vararg tagsData: PlaceDbEntity) {
        deleteAllPlace()
        insertPlace(*tagsData)
    }
}