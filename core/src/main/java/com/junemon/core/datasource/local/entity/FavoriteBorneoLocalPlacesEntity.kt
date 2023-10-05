package com.junemon.core.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.junemon.core.domain.common.dto.Dto
import com.junemon.core.domain.model.FavoriteBorneoPlace

@Entity(tableName = "favorite_place_table")
data class FavoriteBorneoLocalPlacesEntity(
    @PrimaryKey(autoGenerate = true) var favoriteLocalPlaceID: Int?,
    @ColumnInfo(name = "place_id") var localPlaceID: Int?,
    @ColumnInfo(name = "place_type") var placeType: String?,
    @ColumnInfo(name = "place_city") var placeCity: String?,
    @ColumnInfo(name = "place_name") var placeName: String?,
    @ColumnInfo(name = "place_address") var placeAddres: String?,
    @ColumnInfo(name = "place_district") var placeDistrict: String?,
    @ColumnInfo(name = "place_detail") var placeDetail: String?,
    @ColumnInfo(name = "place_picture") var placePicture: String?
) : Dto {

    override fun toDomain(): FavoriteBorneoPlace {
        return FavoriteBorneoPlace(
            favoriteLocalPlaceID = favoriteLocalPlaceID ?: 0,
            localPlaceID = localPlaceID ?: 0,
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