package com.junemon.core.domain.model

import com.junemon.core.domain.common.dto.Domain

data class BorneoPlaces(
    val localPlaceID: Int?,
    val placeCity:String,
    val placeType: String,
    val placeName: String,
    val placeAddress: String,
    val placeDistrict: String,
    val placeDetail: String,
    val placePicture: String
): Domain