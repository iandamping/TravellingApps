package com.junemon.gamesapi.data.datasource.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ian.app.helper.util.fromJsonHelper
import com.junemon.gamesapi.data.datasource.model.sharedGson.gson
import com.junemon.gamesapi.domain2.model.GamesItemData
import com.junemon.gamesapi.domain2.model.PublisherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Entity(tableName = "publishers_entity")
data class PublishersDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "publisher_id")val publisherID: Int,
    @ColumnInfo(name = "publisher_name")val publisherName: String,
    @ColumnInfo(name = "publisher_photo")val publisherPhoto: String,
    @ColumnInfo(name = "publisher_games")val publisherGames: String
)

fun PublishersDbEntity.mapToDomain():PublisherData = PublisherData(publisherName,gson.fromJsonHelper<List<GamesItemData>>(publisherGames),publisherID,publisherPhoto)
fun List<PublishersDbEntity>.mapToDomain():List<PublisherData> = map { it.mapToDomain() }

fun Flow<List<PublishersDbEntity>>.mapToDomain() = map { it.mapToDomain() }

fun PublisherData.mapToDatabase(): PublishersDbEntity = PublishersDbEntity(id, name, imageBackground, gson.toJson(games))
fun List<PublisherData>.mapToDatabase(): List<PublishersDbEntity> = map { it?.mapToDatabase() }


