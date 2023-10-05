package com.ian.junemon.holidaytoborneo.core.datasource.local

import com.junemon.core.datasource.local.entity.BorneoLocalPlacesEntity
import com.junemon.core.datasource.local.entity.FavoriteBorneoLocalPlacesEntity
import kotlinx.coroutines.flow.Flow

interface PlacesLocalDataSource {

    fun getSavedPlaces(): Flow<List<BorneoLocalPlacesEntity>>

    fun getSavedPlacesById(id: Int): Flow<BorneoLocalPlacesEntity?>

    fun getSavedPlacesByType(placeType: String): Flow<List<BorneoLocalPlacesEntity>>

    fun getFavoriteSavedPlaces(): Flow<List<FavoriteBorneoLocalPlacesEntity>>

    suspend fun savePlace(data: List<BorneoLocalPlacesEntity>)

    suspend fun saveFavoritePlace(data: FavoriteBorneoLocalPlacesEntity)

    suspend fun delete()

    suspend fun deleteFavoritePlace(id:Int)

    fun loadSharedPreferenceFilter(): Flow<String>

    suspend fun setSharedPreferenceFilter(data: String)
}