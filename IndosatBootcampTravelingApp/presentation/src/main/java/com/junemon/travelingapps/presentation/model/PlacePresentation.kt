package com.junemon.travelingapps.presentation.model

import com.junemon.travellingapps.domain.model.PlaceCacheData
import com.junemon.travellingapps.domain.model.PlaceRemoteData

data class PlaceCachePresentation(
    var localPlaceID: Int?,
    var placeType: String?,
    var placeName: String?,
    var placeAddres: String?,
    var placeDistrict: String?,
    var placeDetail: String?,
    var placePicture: String?
)

data class PlaceRemotePresentation(
    var placeType: String?,
    var placeName: String?,
    var placeAddres: String?,
    var placeDistrict: String?,
    var placeDetail: String?,
    var placePicture: String?
)

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
