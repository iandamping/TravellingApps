package com.junemon.core.datasource.local.db

import androidx.room.*
import com.junemon.core.datasource.local.entity.BorneoLocalPlacesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BorneoPlacesDao {
    @Query("SELECT * FROM place_table")
    fun loadAllPlace(): Flow<List<BorneoLocalPlacesEntity>>

    @Query("SELECT * FROM place_table WHERE localPlaceID=:id")
    fun loadPlaceById(id:Int): Flow<BorneoLocalPlacesEntity?>

    @Query("SELECT * FROM place_table WHERE place_type = :placeType")
    fun loadAllPlaceByType(placeType: String): Flow<List<BorneoLocalPlacesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(vararg tagsData: BorneoLocalPlacesEntity)

    @Query("DELETE FROM place_table")
    suspend fun deleteAllPlace()

    @Transaction
    suspend fun insertAllPlace(vararg tagsData: BorneoLocalPlacesEntity) {
        deleteAllPlace()
        insertPlace(*tagsData)
    }
}