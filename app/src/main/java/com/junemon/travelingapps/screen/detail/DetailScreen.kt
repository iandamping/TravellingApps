package com.junemon.travelingapps.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.junemon.travelingapps.R
import com.junemon.travelingapps.screen.detail.ParallaxEffectConstant.headerHeight
import com.junemon.travelingapps.screen.detail.ParallaxEffectConstant.toolbarHeight
import com.junemon.travelingapps.viewmodel.DetailPlaceViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailPlaceViewModel: DetailPlaceViewModel = hiltViewModel(),
    onSelectedSimilarPlaces: (Int, String) -> Unit,
    onUpButtonPress: () -> Unit
) {
    val uiState = detailPlaceViewModel.uiState.collectAsState()
    val uiStateFiltered by detailPlaceViewModel.uiStateFiltered.collectAsState()
    val uiStateFavorite by detailPlaceViewModel.uiStateFavorite.collectAsState()
    val scroll: ScrollState = rememberScrollState(0)

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }



    when {
        uiState.value.data != null -> {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                DetailHeader(
                    scroll = scroll,
                    headerHeightPx = headerHeightPx,
                    placePicture = uiState.value.data!!.placePicture
                )
                DetailBody(
                    modifier = Modifier.padding(8.dp),
                    uiStateFiltered = uiStateFiltered,
                    scroll = scroll,
                    placeId = uiState.value.data!!.localPlaceID!!,
                    placeCity = uiState.value.data!!.placeCity,
                    placeAddress = uiState.value.data!!.placeAddress,
                    placeDistrict = uiState.value.data!!.placeDistrict,
                    placeDetail = uiState.value.data!!.placeDetail,
                    isItBookmarked = uiStateFavorite.isBookmarked,
                    onSelectedSimilarPlaces = { placeId, placeType ->
                        onSelectedSimilarPlaces.invoke(placeId, placeType)
                    },
                    onBookMarkPressed = {
                        if (uiStateFavorite.bookmarkedId != null) {
                            detailPlaceViewModel.deleteFavoriteData(uiStateFavorite.bookmarkedId!!)
                        } else {
                            detailPlaceViewModel.saveFavoriteData(uiState.value.data!!)
                        }
                    }
                )
                DetailToolbar(
                    scroll = scroll,
                    headerHeightPx = headerHeightPx,
                    toolbarHeightPx = toolbarHeightPx,
                    onUpButtonPress = onUpButtonPress,
                    isItBookmarked = uiStateFavorite.isBookmarked,
                    onBookMarkPressed = {
                        if (uiStateFavorite.bookmarkedId != null) {
                            detailPlaceViewModel.deleteFavoriteData(uiStateFavorite.bookmarkedId!!)
                        } else {
                            detailPlaceViewModel.saveFavoriteData(uiState.value.data!!)
                        }
                    }
                )
                DetailTitle(
                    scroll = scroll,
                    headerHeightPx = headerHeightPx,
                    toolbarHeightPx = toolbarHeightPx,
                    titleText = uiState.value.data!!.placeName
                )
            }
        }

        uiState.value.errorMessage.isNotEmpty() -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = stringResource(id = R.string.no_data_available)
                )

                Text(
                    text = stringResource(id = R.string.no_data_available),
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }

}