package com.junemon.model.data

data class PlaceRemoteEntity(
    var placeType: String?,
    var placeName: String?,
    var placeAddres: String?,
    var placeDistrict: String?,
    var placeDetail: String?,
    var placePicture: String?
) {
    constructor() : this(
        null, null, null, null, null, null
    )
}