package com.junemon.gamesapi.data.datasource.cache

import com.junemon.gamesapi.data.data.datasource.GameCacheDataSource
import com.junemon.gamesapi.data.datasource.model.mapToDatabase
import com.junemon.gamesapi.data.datasource.model.mapToDomain
import com.junemon.gamesapi.data.db.GameDatabase
import com.junemon.gamesapi.domain2.model.GameData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 31,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class GameCacheDataSourceImpl(private val db: GameDatabase) : GameCacheDataSource {

    override suspend fun set(data: List<GameData>) {
        db.gameDao().insertGame(*data.mapToDatabase().toTypedArray())
    }

    override fun get(): Flow<List<GameData>> {
        return db.gameDao().loadGame().mapToDomain()
    }
}