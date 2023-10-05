package com.junemon.travelingapps.state

import com.junemon.core.domain.model.BorneoPlaces

data class DetailPlaceUiState(
    val data: BorneoPlaces?,
    val errorMessage: String
) {
    companion object {
        fun initialize(): DetailPlaceUiState {
            return DetailPlaceUiState(
                data = null,
                errorMessage = ""
            )
        }
    }
}

