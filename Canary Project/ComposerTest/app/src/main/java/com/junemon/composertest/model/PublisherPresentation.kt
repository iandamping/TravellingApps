package com.junemon.gamesapi.model

import androidx.compose.Model
import com.junemon.gamesapi.domain2.model.GamesItemData
import com.junemon.gamesapi.domain2.model.PublisherData

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Model
data class PublisherPresentation(
    val name: String = "",
    val games: List<GamesItemPresentation>?,
    val id: Int = 0,
    val imageBackground: String = ""
)

@Model
data class GamesItemPresentation(
    val added: Int = 0,
    val name: String = "",
    val id: Int = 0,
    val slug: String = ""
)

fun GamesItemData.mapToPresentation():GamesItemPresentation = GamesItemPresentation(added, name, id, slug)
fun List<GamesItemData>.mapItemToPresentation():List<GamesItemPresentation> = map { it?.mapToPresentation() }

fun PublisherData.mapToPresentation():PublisherPresentation = PublisherPresentation(name,games?.mapItemToPresentation())
fun List<PublisherData>.mapListToPresentation():List<PublisherPresentation> = map { it.mapToPresentation() }