package com.junemon.core.domain.usecase

import com.junemon.core.domain.common.UsecaseHelper
import com.junemon.core.domain.model.BorneoPlaces
import com.junemon.core.domain.model.FavoriteBorneoPlace
import kotlinx.coroutines.flow.Flow

interface PlaceUseCase {

    fun getData(): Flow<UsecaseHelper<List<BorneoPlaces>>>

    fun getDataByType(type: String): Flow<UsecaseHelper<List<BorneoPlaces>>>

    fun getDataById(id: Int): Flow<UsecaseHelper<BorneoPlaces>>

    fun getFavoriteData(): Flow<UsecaseHelper<List<FavoriteBorneoPlace>>>

    suspend fun saveFavoritePlace(data: BorneoPlaces)

    suspend fun deleteFavoritePlace(id:Int)

    fun loadSharedPreferenceFilter(): Flow<String>

    suspend fun setSharedPreferenceFilter(data: String)
}