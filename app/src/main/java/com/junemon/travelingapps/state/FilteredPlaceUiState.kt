package com.junemon.travelingapps.state

import com.junemon.core.domain.model.BorneoPlaces

data class FilteredPlaceUiState(
    val data: List<BorneoPlaces>,
    val errorMessage: String
) {
    companion object {
        fun initialize(): FilteredPlaceUiState {
            return FilteredPlaceUiState(
                data = emptyList(),
                errorMessage = ""
            )
        }
    }
}
