package com.junemon.gamesapi.domain2.repository

import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.domain2.model.GameData

/**
 * Created by Ian Damping on 31,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface GamesRepository {

    fun get(): LiveData<ResultToConsume<List<GameData>>>
}