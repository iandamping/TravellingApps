package com.junemon.model.domain

data class PlaceRemoteData(
    var placeType: String?,
    var placeName: String?,
    var placeAddres: String?,
    var placeDistrict: String?,
    var placeDetail: String?,
    var placePicture: String?
){
    constructor() : this(
        null, null, null, null, null, null
    )
}