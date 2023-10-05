package com.junemon.core.domain.repository

import com.junemon.core.datasource.local.entity.BorneoLocalPlacesEntity
import com.junemon.core.datasource.local.entity.FavoriteBorneoLocalPlacesEntity
import com.junemon.core.domain.common.DomainHelper
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {

    fun getData(): Flow<DomainHelper<List<BorneoLocalPlacesEntity>>>

    fun getDataByType(type:String): Flow<DomainHelper<List<BorneoLocalPlacesEntity>>>

    fun getDataById(id:Int): Flow<DomainHelper<BorneoLocalPlacesEntity>>

    fun getFavoriteData(): Flow<DomainHelper<List<FavoriteBorneoLocalPlacesEntity>>>

    suspend fun saveFavoritePlace(data: FavoriteBorneoLocalPlacesEntity)

    suspend fun deleteFavoritePlace(id:Int)

    fun loadSharedPreferenceFilter(): Flow<String>

    suspend fun setSharedPreferenceFilter(data: String)
}