package com.junemon.gamesapi.ui

import com.ian.app.helper.base.BaseViewModel
import com.junemon.gamesapi.domain2.usecase.PublisherUseCase

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PublisherDataViewModel(private val repository:PublisherUseCase): BaseViewModel() {

    fun get() = repository.get()
}