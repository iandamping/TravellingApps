package com.junemon.travelingapps.state

data class FavoriteDetailPlaceUiState(
    val isBookmarked: Boolean,
    val bookmarkedId: Int?
) {
    companion object {
        fun initialize(): FavoriteDetailPlaceUiState {
            return FavoriteDetailPlaceUiState(
                isBookmarked = false,
                bookmarkedId = null
            )
        }
    }
}

