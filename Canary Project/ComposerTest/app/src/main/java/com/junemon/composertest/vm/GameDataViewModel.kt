package com.junemon.gamesapi.ui

import com.ian.app.helper.base.BaseViewModel
import com.junemon.gamesapi.domain2.usecase.GamesUseCase

/**
 * Created by Ian Damping on 29,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class GameDataViewModel(private val repository: GamesUseCase) : BaseViewModel() {

    fun get() = repository.get()
}