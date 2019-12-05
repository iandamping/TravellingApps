package com.junemon.travelingapps.presentation.model

import com.junemon.travellingapps.domain.model.PlaceRemoteData

data class PlaceCachePresentation(
    var localPlaceID: Int?,
    var placeType: String?,
    var placeName: String?,
    var placeAddres: String?,
    var placeCity: String?,
    var placeDetail: String?,
    var placePicture: String?
)

data class PlaceRemotePresentation(
    var placeType: String?,
    var placeName: String?,
    var placeAddres: String?,
    var placeCity: String?,
    var placeDetail: String?,
    var placePicture: String?
)

fun PlaceRemoteData.mapRemoteToCachePresentation(): PlaceCachePresentation = PlaceCachePresentation(
    null,
    placeType,
    placeName,
    placeAddres,
    placeCity,
    placeDetail,
    placePicture
)

fun List<PlaceRemoteData>.mapRemoteToCachePresentation(): List<PlaceCachePresentation> =
    map { it.mapRemoteToCachePresentation() }

