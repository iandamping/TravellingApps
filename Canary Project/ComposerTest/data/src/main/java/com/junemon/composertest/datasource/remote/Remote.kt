package com.junemon.gamesapi.data.datasource.remote

import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.data.datasource.model.GamesEntity
import com.junemon.gamesapi.data.datasource.model.PublishersEntity
import com.junemon.gamesapi.data.datasource.model.ResultEntity
import com.junemon.gamesapi.data.datasource.remote.ApiConstant.baseUrl
import com.junemon.gamesapi.data.datasource.remote.ApiConstant.games
import com.junemon.gamesapi.data.datasource.remote.ApiConstant.publisher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Ian Damping on 31,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface GamesApi {

    @GET(games)
    suspend fun getGames(): Response<ResultEntity<GamesEntity>>

    @GET(publisher)
    suspend fun getPublisher(): Response<ResultEntity<PublishersEntity>>

}

object ApiConstant {
    const val baseUrl = "https://api.rawg.io/api/"
    const val games = "games"
    const val publisher = "publishers"
}