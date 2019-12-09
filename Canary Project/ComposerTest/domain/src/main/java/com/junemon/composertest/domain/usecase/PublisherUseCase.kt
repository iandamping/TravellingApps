package com.junemon.gamesapi.domain2.usecase

import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.domain2.model.PublisherData
import com.junemon.gamesapi.domain2.repository.PublisherRepository

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PublisherUseCase(private val repository: PublisherRepository) {
    fun get(): LiveData<ResultToConsume<List<PublisherData>>> = repository.get()
}