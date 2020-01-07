package com.junemon.gamesapi.domain2.repository

import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.domain2.model.PublisherData
import io.reactivex.Single

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PublisherRepository {
    fun get(): Single<List<PublisherData>>
}