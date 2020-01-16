package com.junemon.core.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
data class PlaceDbEntity(
    @PrimaryKey(autoGenerate = true) var localPlaceID: Int?,
    @ColumnInfo(name = "place_type") var placeType: String?,
    @ColumnInfo(name = "place_name") var placeName: String?,
    @ColumnInfo(name = "place_address") var placeAddres: String?,
    @ColumnInfo(name = "place_city") var placeDistrict: String?,
    @ColumnInfo(name = "place_detail") var placeDetail: String?,
    @ColumnInfo(name = "place_picture") var placePicture: String?
) {
    constructor() : this(
        null, null, null, null, null, null, null
    )
}