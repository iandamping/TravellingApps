package com.junemon.travellingapps.domain.model

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class PlaceCacheData(
    var localPlaceID: Int?,
    var placeType: String?,
    var placeName: String?,
    var placeAddres: String?,
    var placeDistrict: String?,
    var placeDetail: String?,
    var placePicture: String?
)

data class PlaceRemoteData(
    var placeType: String?,
    var placeName: String?,
    var placeAddres: String?,
    var placeDistrict: String?,
    var placeDetail: String?,
    var placePicture: String?
)