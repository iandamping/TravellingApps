package com.junemon.gamesapi.data.datasource.remote

import com.ian.app.helper.base.BaseDataSource
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.data.data.datasource.GamesRemoteDataSource
import com.junemon.gamesapi.data.datasource.model.mapToDomain
import com.junemon.gamesapi.domain2.model.GameData
import kotlinx.coroutines.CompletableDeferred

/**
 * Created by Ian Damping on 31,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class GamesRemoteDataSourceImpl(private val api: GamesApi) : BaseDataSource(),
    GamesRemoteDataSource {

    override suspend fun get(): ResultToConsume<List<GameData>> {
        val results = CompletableDeferred<ResultToConsume<List<GameData>>>()
        try {
            val firstData = api.getGames().doOneShot()
            val firstDataMap = firstData.data?.data?.mapToDomain()
            checkNotNull(firstDataMap){
                " ${firstData.message} "
            }
            assert(firstDataMap.isNotEmpty())
            results.complete(ResultToConsume(firstData.status, firstDataMap, firstData.message))
        } catch (e: Exception) {
            results.complete(ResultToConsume(ResultToConsume.Status.ERROR, null, e.message))
        }
        return results.await()

    }
}