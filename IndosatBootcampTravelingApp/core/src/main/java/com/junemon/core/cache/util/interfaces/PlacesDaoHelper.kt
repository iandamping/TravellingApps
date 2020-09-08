package com.junemon.core.cache.util.interfaces

import com.junemon.core.cache.model.PlaceDbEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PlacesDaoHelper {

    fun loadAllPlace(): Flow<List<PlaceDbEntity>>

    fun loadAllBalanceByMonth(placeType: String): Flow<List<PlaceDbEntity>>

    suspend fun deleteAllPlace()

    suspend fun insertAllPlace(vararg tagsData: PlaceDbEntity)
}