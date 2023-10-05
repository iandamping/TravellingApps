package com.junemon.travelingapps.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junemon.travelingapps.navigation.PlaceDestinationArgument
import com.junemon.travelingapps.state.DetailPlaceUiState
import com.junemon.travelingapps.state.FavoriteDetailPlaceUiState
import com.junemon.travelingapps.state.FilteredPlaceUiState
import com.junemon.core.domain.common.UsecaseHelper
import com.junemon.core.domain.model.BorneoPlaces
import com.junemon.core.domain.usecase.PlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DetailPlaceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: PlaceUseCase
) : ViewModel() {

    private val detailPlaceId =
        savedStateHandle.getStateFlow(PlaceDestinationArgument.DetailPlacesId.name, 0)

    private val detailPlaceType =
        savedStateHandle.getStateFlow(PlaceDestinationArgument.DetailPlacesType.name, "")

    private val _uiState: MutableStateFlow<DetailPlaceUiState> =
        MutableStateFlow(DetailPlaceUiState.initialize())
    val uiState: StateFlow<DetailPlaceUiState> = _uiState.asStateFlow()

    private val _uiStateFiltered: MutableStateFlow<FilteredPlaceUiState> =
        MutableStateFlow(FilteredPlaceUiState.initialize())
    val uiStateFiltered: StateFlow<FilteredPlaceUiState> = _uiStateFiltered.asStateFlow()

    private val _uiStateFavorite: MutableStateFlow<FavoriteDetailPlaceUiState> =
        MutableStateFlow(FavoriteDetailPlaceUiState.initialize())
    val uiStateFavorite: StateFlow<FavoriteDetailPlaceUiState> = _uiStateFavorite.asStateFlow()

    fun saveFavoriteData(data: BorneoPlaces) {
        viewModelScope.launch {
            useCase.saveFavoritePlace(data)
        }
    }

    fun deleteFavoriteData(id: Int) {
        viewModelScope.launch {
            useCase.deleteFavoritePlace(id)
        }
    }

    init {
        viewModelScope.launch {
            detailPlaceId.flatMapLatest { id ->
                useCase.getDataById(id)
            }.collect { result ->
                when (result) {
                    is UsecaseHelper.Error -> _uiState.update { currentSate ->
                        currentSate.copy(errorMessage = result.errorMessage)
                    }

                    is UsecaseHelper.Success -> _uiState.update { currentSate ->
                        currentSate.copy(data = result.data)
                    }
                }
            }
        }
        viewModelScope.launch {
            detailPlaceType.flatMapLatest { type ->
                useCase.getDataByType(type)
            }.collect { result ->
                when (result) {
                    is UsecaseHelper.Error -> _uiStateFiltered.update { currentSate ->
                        currentSate.copy(errorMessage = result.errorMessage)
                    }

                    is UsecaseHelper.Success -> _uiStateFiltered.update { currentSate ->
                        currentSate.copy(data = result.data.shuffled().take(2))
                    }
                }
            }
        }

        viewModelScope.launch {
            detailPlaceId.collectLatest { id ->
                combine(useCase.getDataById(id), useCase.getFavoriteData()) { a, b ->
                    Pair(a, b)
                }.collectLatest { (borneoPlace, favBorneoPlace) ->
                    when {
                        borneoPlace is UsecaseHelper.Success && favBorneoPlace is UsecaseHelper.Success -> {
                            _uiStateFavorite.update { currentSate ->
                                currentSate.copy(
                                    isBookmarked = favBorneoPlace.data.any { it.localPlaceID == borneoPlace.data.localPlaceID!! },
                                    bookmarkedId =
                                    favBorneoPlace.data.firstOrNull { it.localPlaceID == uiState.value.data!!.localPlaceID!! }?.favoriteLocalPlaceID
                                )
                            }
                        }

                        borneoPlace is UsecaseHelper.Error && favBorneoPlace is UsecaseHelper.Error -> {
                            _uiStateFavorite.update { currentSate ->
                                currentSate.copy(bookmarkedId = null, isBookmarked = false)
                            }
                        }

                        borneoPlace is UsecaseHelper.Error -> {
                            _uiStateFavorite.update { currentSate ->
                                currentSate.copy(bookmarkedId = null, isBookmarked = false)
                            }
                        }

                        favBorneoPlace is UsecaseHelper.Error -> {
                            _uiStateFavorite.update { currentSate ->
                                currentSate.copy(bookmarkedId = null, isBookmarked = false)
                            }
                        }
                    }
                }
            }
        }
    }
}