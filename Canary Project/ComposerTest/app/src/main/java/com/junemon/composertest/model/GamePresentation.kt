package com.junemon.gamesapi.model

import androidx.compose.Model
import com.junemon.gamesapi.domain2.model.GameData

/**
 * Created by Ian Damping on 31,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Model
data class GamePresentation(
    val id: Int = 0,
    val slug: String = "",
    val released: String = "",
    val backgroundImage: String = "",
    val name: String = ""
)

fun GameData.mapToPresentation():GamePresentation = GamePresentation(id, slug, released, backgroundImage, name)

fun List<GameData>.mapToPresentation(): List<GamePresentation> = map { it.mapToPresentation() }
