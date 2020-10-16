package com.junemon.core.cache.util.classes

import com.junemon.core.cache.db.PlaceDao
import com.junemon.core.cache.model.PlaceDbEntity
import com.junemon.core.cache.util.interfaces.PlacesDaoHelper
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PlacesDaoHelperImpl(private val placeDao: PlaceDao) : PlacesDaoHelper {

    override fun loadAllPlace(): Flow<List<PlaceDbEntity>> {
        return placeDao.loadAllPlace()
    }

    override fun loadAllBalanceByMonth(placeType: String): Flow<List<PlaceDbEntity>> {
        return placeDao.loadAllBalanceByMonth(placeType)
    }

    override suspend fun deleteAllPlace() {
        placeDao.deleteAllPlace()
    }

    override suspend fun insertAllPlace(vararg tagsData: PlaceDbEntity) {
        placeDao.insertAllPlace(*tagsData)
    }
}