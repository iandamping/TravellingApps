package com.junemon.gamesapi.domain2.repository

import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.domain2.model.PublisherData

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PublisherRepository {
    fun get(): LiveData<ResultToConsume<List<PublisherData>>>
}