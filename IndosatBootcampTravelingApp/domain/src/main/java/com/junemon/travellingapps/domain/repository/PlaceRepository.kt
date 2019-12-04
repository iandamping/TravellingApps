package com.junemon.travellingapps.domain.repository

import androidx.lifecycle.LiveData
import com.junemon.travellingapps.domain.model.PlaceCacheData

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PlaceRepository {

    fun getCache(): LiveData<List<PlaceCacheData>>

    suspend fun delete()

    suspend fun setCache()
}