package com.junemon.gamesapi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.junemon.gamesapi.data.datasource.model.PublishersDbEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Dao
interface PublisherDao {
    @Query("SELECT * FROM publishers_entity")
    fun loadGame(): Flow<List<PublishersDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(vararg gameData: PublishersDbEntity)

    @Query("DELETE FROM publishers_entity")
    suspend fun deleteAllGame()
}