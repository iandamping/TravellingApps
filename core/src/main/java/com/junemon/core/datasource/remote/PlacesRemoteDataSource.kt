package com.junemon.core.datasource.remote

import com.junemon.core.datasource.remote.model.BorneoPlacesData
import com.junemon.core.domain.common.DataHelper
import kotlinx.coroutines.flow.Flow

interface PlacesRemoteDataSource {

    fun getFirebaseData(): Flow<DataHelper<List<BorneoPlacesData>>>
}