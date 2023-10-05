package com.junemon.core.domain.model

import com.junemon.core.domain.common.dto.Domain

data class FavoriteBorneoPlace(
    val favoriteLocalPlaceID: Int?,
    val localPlaceID: Int,
    val placeCity: String,
    val placeType: String,
    val placeName: String,
    val placeAddress: String,
    val placeDistrict: String,
    val placeDetail: String,
    val placePicture: String
): Domain

fun FavoriteBorneoPlace.mapToBorneoPlace(): BorneoPlaces = BorneoPlaces(
    localPlaceID = localPlaceID,
    placeCity = placeCity,
    placeType = placeType,
    placeName = placeName,
    placeAddress = placeAddress,
    placeDistrict = placeDistrict,
    placeDetail = placeDetail,
    placePicture = placePicture
)