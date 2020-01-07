package com.junemon.model.presentation.dto

import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.PlaceRemotePresentation

fun PlaceRemoteData.mapRemoteToPresentation(): PlaceRemotePresentation = PlaceRemotePresentation(
    placeType,
    placeName,
    placeAddres,
    placeDistrict,
    placeDetail,
    placePicture
)

fun PlaceCacheData.mapCacheToPresentation(): PlaceCachePresentation = PlaceCachePresentation(
    null,
    placeType,
    placeName,
    placeAddres,
    placeDistrict,
    placeDetail,
    placePicture
)

fun List<PlaceRemoteData>.mapRemoteToPresentation(): List<PlaceRemotePresentation> =
    map { it.mapRemoteToPresentation() }

fun List<PlaceCacheData>.mapCacheToPresentation(): List<PlaceCachePresentation> =
    map { it.mapCacheToPresentation() }