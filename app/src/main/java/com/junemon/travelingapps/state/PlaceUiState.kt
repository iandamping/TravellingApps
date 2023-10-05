package com.junemon.travelingapps.state

import com.junemon.core.domain.model.BorneoPlaces


data class PlaceUiState(
    val isLoading: Boolean,
    val data: List<BorneoPlaces>,
    val errorMessage: String
) {
    companion object {
        fun initialize(): PlaceUiState {
            return PlaceUiState(
                isLoading = true,
                data = emptyList(),
                errorMessage = ""
            )
        }
    }
}
