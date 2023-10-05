package com.junemon.core.datasource.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.junemon.core.datasource.local.entity.FavoriteBorneoLocalPlacesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBorneoPlacesDao {

    @Query("SELECT * FROM favorite_place_table")
    fun loadAllFavoritePlace(): Flow<List<FavoriteBorneoLocalPlacesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePlace(vararg tagsData: FavoriteBorneoLocalPlacesEntity)

    @Query("DELETE FROM favorite_place_table")
    suspend fun deleteAllFavoritePlace()

    @Query("DELETE FROM favorite_place_table where favoriteLocalPlaceID = :selectedId")
    suspend fun deleteSelectedId(selectedId: Int)

}