package com.junemon.gamesapi.domain2.usecase

import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.domain2.model.GameData
import com.junemon.gamesapi.domain2.repository.GamesRepository

/**
 * Created by Ian Damping on 31,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class GamesUseCase(private val gameRepository: GamesRepository) {

    fun get(): LiveData<ResultToConsume<List<GameData>>> = gameRepository.get()
}