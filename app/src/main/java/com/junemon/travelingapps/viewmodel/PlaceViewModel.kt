package com.junemon.travelingapps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junemon.core.domain.common.UsecaseHelper
import com.junemon.core.domain.model.mapToBorneoPlace
import com.junemon.core.domain.usecase.PlaceUseCase
import com.junemon.travelingapps.screen.home.filter.PlaceFilterItem.FILTER_0
import com.junemon.travelingapps.screen.home.filter.PlaceFilterItem.FILTER_4
import com.junemon.travelingapps.state.PlaceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(private val useCase: PlaceUseCase) : ViewModel() {

    private val _filterPlace: MutableStateFlow<String> = MutableStateFlow("")
    val filterPlace: StateFlow<String> = _filterPlace.asStateFlow()

    private val _searchPlace: MutableStateFlow<String> = MutableStateFlow("")
    val searchPlace: StateFlow<String> = _searchPlace.asStateFlow()

    private val _uiState: MutableStateFlow<PlaceUiState> =
        MutableStateFlow(PlaceUiState.initialize())
    val uiState: StateFlow<PlaceUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            useCase.loadSharedPreferenceFilter().collectLatest { filter ->
                _filterPlace.value = filter
            }
        }

        viewModelScope.launch {
            combine(
                useCase.loadSharedPreferenceFilter(),
                useCase.getData(),
                useCase.getFavoriteData()
            ) { filter, place, favPlace ->
                Triple(filter, place, favPlace)
            }.collectLatest {
                if (it.first == FILTER_4) {
                    when (val favPlace = it.third) {
                        is UsecaseHelper.Success -> {
                            _uiState.update { currentUiState ->
                                currentUiState.copy(
                                    isLoading = false,
                                    data = favPlace.data.map { item -> item.mapToBorneoPlace() },
                                    errorMessage = ""
                                )
                            }
                        }

                        is UsecaseHelper.Error -> {
                            _uiState.update { currentUiState ->
                                currentUiState.copy(
                                    isLoading = false,
                                    data = emptyList(),
                                    errorMessage = favPlace.errorMessage
                                )
                            }
                        }
                    }
                } else {
                    when (val place = it.second) {
                        is UsecaseHelper.Success -> {
                            when {
                                it.first.isEmpty() || it.first == FILTER_0 -> {
                                    _uiState.update { currentUiState ->
                                        currentUiState.copy(
                                            isLoading = false,
                                            data = place.data,
                                            errorMessage = ""
                                        )
                                    }
                                }

                                else -> {
                                    _uiState.update { currentUiState ->
                                        currentUiState.copy(
                                            isLoading = false,
                                            data = place.data.filter { item -> item.placeType == it.first },
                                            errorMessage = ""
                                        )
                                    }
                                }
                            }
                        }

                        is UsecaseHelper.Error -> {
                            _uiState.update { currentUiState ->
                                currentUiState.copy(
                                    isLoading = false,
                                    data = emptyList(),
                                    errorMessage = place.errorMessage
                                )
                            }
                        }
                    }
                }
            }

        }
    }

    fun setSearchPlace(query: String) {
        _searchPlace.value = query
    }

    fun setFilterData(data: String) {
        viewModelScope.launch {
            useCase.setSharedPreferenceFilter(data)
        }
    }
}