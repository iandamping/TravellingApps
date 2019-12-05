package com.junemon.travelingapps.data.datasource.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.junemon.travellingapps.domain.model.PlaceCacheData
import com.junemon.travellingapps.domain.model.PlaceRemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
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

data class PlaceRemoteEntity(
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

fun PlaceDbEntity.mapToCacheDomain(): PlaceCacheData = PlaceCacheData(localPlaceID, placeType, placeName, placeAddres, placeDistrict, placeDetail, placePicture)

fun PlaceRemoteEntity.mapToRemoteDomain(): PlaceRemoteData = PlaceRemoteData(placeType, placeName, placeAddres, placeDistrict, placeDetail, placePicture)

fun PlaceRemoteData.mapRemoteToCacheDomain(): PlaceCacheData = PlaceCacheData(null, placeType, placeName, placeAddres, placeDistrict, placeDetail, placePicture)

fun PlaceCacheData.mapToDatabase(): PlaceDbEntity = PlaceDbEntity(null, placeType, placeName, placeAddres, placeDistrict, placeDetail, placePicture)

fun List<PlaceCacheData>.mapToDatabase(): List<PlaceDbEntity> = map { it.mapToDatabase() }

fun List<PlaceRemoteData>.mapRemoteToCacheDomain(): List<PlaceCacheData> = map { it.mapRemoteToCacheDomain() }

fun List<PlaceDbEntity>.mapToCacheDomain(): List<PlaceCacheData> = map { it.mapToCacheDomain() }

fun List<PlaceRemoteEntity>.mapToRemoteDomain(): List<PlaceRemoteData> = map { it.mapToRemoteDomain() }

fun Flow<List<PlaceDbEntity>>.mapToDomain() = map { it.mapToCacheDomain() }
