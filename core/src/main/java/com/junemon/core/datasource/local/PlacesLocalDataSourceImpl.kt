package com.junemon.core.datasource.local

import com.ian.junemon.holidaytoborneo.core.datasource.local.PlacesLocalDataSource
import com.junemon.core.datasource.local.datastore.DataConstant
import com.junemon.core.datasource.local.datastore.DataStoreHelper
import com.junemon.core.datasource.local.db.BorneoPlacesDao
import com.junemon.core.datasource.local.db.FavoriteBorneoPlacesDao
import com.junemon.core.datasource.local.entity.BorneoLocalPlacesEntity
import com.junemon.core.datasource.local.entity.FavoriteBorneoLocalPlacesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlacesLocalDataSourceImpl @Inject constructor(
    private val dao: BorneoPlacesDao,
    private val favDao: FavoriteBorneoPlacesDao,
    private val dataHelper: DataStoreHelper
) :
    PlacesLocalDataSource {

    override fun getSavedPlaces(): Flow<List<BorneoLocalPlacesEntity>> {
        return dao.loadAllPlace()
    }

    override fun getSavedPlacesById(id: Int): Flow<BorneoLocalPlacesEntity?> {
        return dao.loadPlaceById(id)
    }

    override fun getSavedPlacesByType(placeType: String): Flow<List<BorneoLocalPlacesEntity>> {
        return dao.loadAllPlaceByType(placeType)
    }

    override fun getFavoriteSavedPlaces(): Flow<List<FavoriteBorneoLocalPlacesEntity>> {
        return favDao.loadAllFavoritePlace()
    }

    override suspend fun savePlace(data: List<BorneoLocalPlacesEntity>) {
        dao.insertAllPlace(*data.toTypedArray())
    }

    override suspend fun saveFavoritePlace(data: FavoriteBorneoLocalPlacesEntity) {
        favDao.insertFavoritePlace(data)
    }

    override suspend fun delete() {
        dao.deleteAllPlace()
    }

    override suspend fun deleteFavoritePlace(id: Int) {
        favDao.deleteSelectedId(id)
    }

    override fun loadSharedPreferenceFilter(): Flow<String> {
        return dataHelper.getStringInDataStore(DataConstant.FILTER_KEY)
    }

    override suspend fun setSharedPreferenceFilter(data: String) {
        dataHelper.saveStringInDataStore(DataConstant.FILTER_KEY, data)
    }
}