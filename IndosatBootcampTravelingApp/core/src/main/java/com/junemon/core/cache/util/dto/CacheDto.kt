package com.junemon.core.cache.util.dto

import com.junemon.core.cache.model.PlaceDbEntity
import com.junemon.model.domain.PlaceCacheData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun PlaceDbEntity.mapToCacheDomain(): PlaceCacheData = PlaceCacheData(
    localPlaceID,
    placeType,
    placeName,
    placeAddres,
    placeDistrict,
    placeDetail,
    placePicture
)

fun PlaceCacheData.mapToDatabase(): PlaceDbEntity =
    PlaceDbEntity(null, placeType, placeName, placeAddres, placeDistrict, placeDetail, placePicture)

fun List<PlaceCacheData>.mapToDatabase(): List<PlaceDbEntity> = map { it.mapToDatabase() }

fun List<PlaceDbEntity>.mapToCacheDomain(): List<PlaceCacheData> = map { it.mapToCacheDomain() }

fun Flow<List<PlaceDbEntity>>.mapToDomain() = map { it.mapToCacheDomain() }