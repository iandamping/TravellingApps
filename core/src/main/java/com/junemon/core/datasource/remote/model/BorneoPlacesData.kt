package com.junemon.core.datasource.remote.model

import com.junemon.core.domain.common.dto.Dto
import com.junemon.core.domain.model.BorneoPlaces
import com.squareup.moshi.Json

data class BorneoPlacesData(
    @Json(name = "placeType") var placeType: String?,
    @Json(name = "placeCity") var placeCity: String?,
    @Json(name = "placeName") var placeName: String?,
    @Json(name = "placeAddres") var placeAddres: String?,
    @Json(name = "placeDistrict") var placeDistrict: String?,
    @Json(name = "placeDetail") var placeDetail: String?,
    @Json(name = "placePicture") var placePicture: String?
) : Dto {
    constructor() : this(
        null, null, null, null, null, null, null
    )

    override fun toDomain(): BorneoPlaces {
        return BorneoPlaces(
            localPlaceID = null,
            placeType = placeType ?: "no data available",
            placeCity = placeCity ?: "no data available",
            placeName = placeName ?: "no data available",
            placeAddress = placeAddres ?: "no data available",
            placeDistrict = placeDistrict ?: "no data available",
            placeDetail = placeDetail ?: "no data available",
            placePicture = placePicture ?: "no data available"
        )
    }
}