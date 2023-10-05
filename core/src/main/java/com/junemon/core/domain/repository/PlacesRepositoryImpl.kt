package com.junemon.core.domain.repository

import androidx.annotation.AnyThread
import com.ian.junemon.holidaytoborneo.core.datasource.local.PlacesLocalDataSource
import com.junemon.core.datasource.local.entity.BorneoLocalPlacesEntity
import com.junemon.core.datasource.local.entity.FavoriteBorneoLocalPlacesEntity
import com.junemon.core.datasource.remote.PlacesRemoteDataSource
import com.junemon.core.datasource.remote.model.BorneoPlacesData
import com.junemon.core.domain.common.DataHelper
import com.junemon.core.domain.common.DomainHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlacesRemoteDataSource,
    private val localDataSource: PlacesLocalDataSource
) : PlacesRepository {

    override fun getData(): Flow<DomainHelper<List<BorneoLocalPlacesEntity>>> {
        return object :
            NetworkBoundResource<List<BorneoLocalPlacesEntity>, List<BorneoPlacesData>>() {

            override fun loadFromDB(): Flow<List<BorneoLocalPlacesEntity>> {
                return localDataSource.getSavedPlaces()
            }

            override fun shouldFetch(data: List<BorneoLocalPlacesEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<DataHelper<List<BorneoPlacesData>>> {
                return remoteDataSource.getFirebaseData()
            }

            override suspend fun saveCallResult(data: List<BorneoPlacesData>) {
                localDataSource.savePlace(data.applyMainSafeSort())
            }
        }.asFlow().catch {
            DomainHelper.Error("Application encounter unknown problem")
        }
    }

    override fun getDataByType(type: String): Flow<DomainHelper<List<BorneoLocalPlacesEntity>>> {
        return localDataSource.getSavedPlacesByType(type).map {
            if (it.isNotEmpty()) {
                DomainHelper.Success(it)
            } else DomainHelper.Error("No data available")
        }.catch {
            DomainHelper.Error("Application encounter unknown problem")
        }
    }

    override fun getDataById(id: Int): Flow<DomainHelper<BorneoLocalPlacesEntity>> {
        return localDataSource.getSavedPlacesById(id).map {
            if (it != null) {
                DomainHelper.Success(it)
            } else DomainHelper.Error("No data available")
        }.catch {
            DomainHelper.Error("Application encounter unknown problem")
        }
    }

    override fun getFavoriteData(): Flow<DomainHelper<List<FavoriteBorneoLocalPlacesEntity>>> {
       return localDataSource.getFavoriteSavedPlaces().map {
           if (it.isNotEmpty()) {
               DomainHelper.Success(it)
           } else DomainHelper.Error("No data available")
       }.catch {
           DomainHelper.Error("Application encounter unknown problem")
       }
    }

    override suspend fun saveFavoritePlace(data: FavoriteBorneoLocalPlacesEntity) {
       localDataSource.saveFavoritePlace(data = data)
    }

    @AnyThread
    private suspend fun List<BorneoPlacesData>.applyMainSafeSort() =
        withContext(Dispatchers.Default) {
            this@applyMainSafeSort.applySort()
        }

    private fun List<BorneoPlacesData>.applySort(): List<BorneoLocalPlacesEntity> {
        return this.map { remoteData ->
            BorneoLocalPlacesEntity(
                localPlaceID = null,
                placeType = remoteData.placeType,
                placeCity = remoteData.placeCity,
                placeName = remoteData.placeName,
                placeAddres = remoteData.placeAddres,
                placeDistrict = remoteData.placeDistrict,
                placeDetail = remoteData.placeDetail,
                placePicture = remoteData.placePicture
            )
        }
    }

    override suspend fun deleteFavoritePlace(id: Int) {
        localDataSource.deleteFavoritePlace(id)
    }

    override fun loadSharedPreferenceFilter(): Flow<String> {
        return localDataSource.loadSharedPreferenceFilter()
    }

    override suspend fun setSharedPreferenceFilter(data: String) {
        localDataSource.setSharedPreferenceFilter(data)
    }
}