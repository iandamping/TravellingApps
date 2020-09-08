package com.junemon.gamesapi.domain2.model

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class PublisherData(
    val name: String = "",
    val games: List<GamesItemData>?,
    val id: Int = 0,
    val imageBackground: String = ""
)

data class GamesItemData(
    val added: Int = 0,
    val name: String = "",
    val id: Int = 0,
    val slug: String = ""
)