package com.junemon.core.domain.usecase

import com.junemon.core.datasource.local.entity.FavoriteBorneoLocalPlacesEntity
import com.junemon.core.domain.common.DomainHelper
import com.junemon.core.domain.common.UsecaseHelper
import com.junemon.core.domain.model.BorneoPlaces
import com.junemon.core.domain.model.FavoriteBorneoPlace
import com.junemon.core.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaceUseCaseImpl @Inject constructor(private val repository: PlacesRepository) :
    PlaceUseCase {
    override fun getData(): Flow<UsecaseHelper<List<BorneoPlaces>>> {
        return repository.getData().map { result ->
            when (result) {
                is DomainHelper.Error -> UsecaseHelper.Error(result.errorMessage)
                is DomainHelper.Success -> UsecaseHelper.Success(result.data.map { it.toDomain() })
            }
        }
    }

    override fun getDataByType(type: String): Flow<UsecaseHelper<List<BorneoPlaces>>> {
        return repository.getDataByType(type).map { result ->
            when (result) {
                is DomainHelper.Error -> UsecaseHelper.Error(result.errorMessage)
                is DomainHelper.Success -> UsecaseHelper.Success(result.data.map { it.toDomain() })
            }
        }
    }

    override fun getDataById(id: Int): Flow<UsecaseHelper<BorneoPlaces>> {
        return repository.getDataById(id).map { result ->
            when (result) {
                is DomainHelper.Error -> UsecaseHelper.Error(result.errorMessage)
                is DomainHelper.Success -> UsecaseHelper.Success(result.data.toDomain())
            }
        }
    }

    override fun getFavoriteData(): Flow<UsecaseHelper<List<FavoriteBorneoPlace>>> {
        return repository.getFavoriteData().map { result ->
            when (result) {
                is DomainHelper.Error -> UsecaseHelper.Error(result.errorMessage)
                is DomainHelper.Success -> UsecaseHelper.Success(result.data.map { it.toDomain() })
            }
        }
    }

    override suspend fun saveFavoritePlace(data: BorneoPlaces) {
        repository.saveFavoritePlace(
            data = FavoriteBorneoLocalPlacesEntity(
                favoriteLocalPlaceID = null,
                localPlaceID = data.localPlaceID,
                placeType = data.placeType,
                placeCity = data.placeCity,
                placeName = data.placeName,
                placeAddres = data.placeAddress,
                placeDistrict = data.placeDistrict,
                placeDetail = data.placeDetail,
                placePicture = data.placePicture
            )
        )
    }

    override suspend fun deleteFavoritePlace(id: Int) {
        repository.deleteFavoritePlace(id)
    }

    override fun loadSharedPreferenceFilter(): Flow<String> {
        return repository.loadSharedPreferenceFilter()
    }

    override suspend fun setSharedPreferenceFilter(data: String) {
        repository.setSharedPreferenceFilter(data)
    }
}