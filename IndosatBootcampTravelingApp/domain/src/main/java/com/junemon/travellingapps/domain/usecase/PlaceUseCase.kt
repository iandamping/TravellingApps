package com.junemon.travellingapps.domain.usecase

import androidx.lifecycle.LiveData
import com.junemon.travellingapps.domain.model.PlaceCacheData
import com.junemon.travellingapps.domain.repository.PlaceRepository

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlaceUseCase(private val repository: PlaceRepository) {

    fun getCache(): LiveData<List<PlaceCacheData>> = repository.getCache()
    suspend fun delete() = repository.delete()
    suspend fun setCache() = repository.setCache()
}