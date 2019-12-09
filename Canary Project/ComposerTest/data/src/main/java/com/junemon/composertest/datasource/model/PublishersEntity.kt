package com.junemon.gamesapi.data.datasource.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.junemon.gamesapi.domain2.model.GamesItemData
import com.junemon.gamesapi.domain2.model.PublisherData

/**
 * Created by Ian Damping on 31,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

object sharedGson {
    val gson = Gson()
}

data class GamesItem(
    @SerializedName("added")
    val added: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("slug")
    val slug: String = ""
)

data class PublishersEntity(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("games")
    val games: List<GamesItem>?,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image_background")
    val imageBackground: String = ""
)
fun GamesItem.mapSingleItemToDomain():GamesItemData = GamesItemData(added, name, id, slug)
fun List<GamesItem>.mapToDomain():List<GamesItemData> = map { it?.mapSingleItemToDomain() }


fun PublishersEntity.mapSingleItemToDomain():PublisherData = PublisherData(name,games?.mapToDomain(),id, imageBackground)
fun List<PublishersEntity>.mapRemoteItemToDomain():List<PublisherData> = map { it.mapSingleItemToDomain() }

