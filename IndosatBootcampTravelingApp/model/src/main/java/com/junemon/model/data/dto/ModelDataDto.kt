package com.junemon.model.data.dto

import com.junemon.model.data.PlaceRemoteEntity
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData

fun PlaceRemoteEntity.mapToRemoteDomain(): PlaceRemoteData =
    PlaceRemoteData(placeType, placeName, placeAddres, placeDistrict, placeDetail, placePicture)

fun PlaceRemoteData.mapRemoteToCacheDomain(): PlaceCacheData = PlaceCacheData(
    null,
    placeType,
    placeName,
    placeAddres,
    placeDistrict,
    placeDetail,
    placePicture
)

fun List<PlaceRemoteData>.mapRemoteToCacheDomain(): List<PlaceCacheData> =
    map { it.mapRemoteToCacheDomain() }

fun List<PlaceRemoteEntity>.mapToRemoteDomain(): List<PlaceRemoteData> =
    map { it.mapToRemoteDomain() }