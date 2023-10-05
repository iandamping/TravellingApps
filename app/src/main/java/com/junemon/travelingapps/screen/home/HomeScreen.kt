package com.junemon.travelingapps.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.junemon.travelingapps.viewmodel.PlaceViewModel
import com.junemon.travelingapps.R
import com.junemon.travelingapps.screen.home.filter.PlaceFilterDialog
import com.junemon.travelingapps.screen.home.filter.PlaceFilterItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaceViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
    onSelectedPlaces: (Int, String) -> Unit
) {

    val filterPlace by viewModel.filterPlace.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val userSearch by viewModel.searchPlace.collectAsState()

    val state = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )

    ModalBottomSheetLayout(
        modifier = modifier,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp
        ),
        sheetState = state,
        sheetContent = {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(id = R.string.filter_place_dialog),
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        IconButton(modifier = Modifier
                            .padding(vertical = 8.dp), onClick = {
                            scope.launch {
                                if (state.isVisible) {
                                    state.hide()
                                }
                            }
                        }, content = {
                            Image(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        })
                    }
                }
                items(PlaceFilterItem.configureFilterItem(filterPlace)) { singleItem ->
                    PlaceFilterDialog(filterItem = singleItem, selectedFilterItem = { filterItem ->
                        scope.launch {
                            if (state.isVisible) {
                                state.hide()
                            }
                        }
                        viewModel.setFilterData(filterItem.filterText)
                    })
                }
            }
        },
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                val (titleRef, contentRef) = createRefs()
                val titleGuideLine = createGuidelineFromTop(0.2f)
                HomeTitle(modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(titleGuideLine)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                    isFilterISClicked = {
                        scope.launch {
                            if (!state.isVisible) {
                                state.show()
                            }
                        }
                    }, userSearch = userSearch, onUserSearch = { query ->
                        viewModel.setSearchPlace(query)
                    })
                HomeContent(
                    modifier = Modifier.constrainAs(contentRef) {
                        top.linkTo(titleGuideLine, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                    userSearch = userSearch,
                    state = uiState,
                    onSelectedPlaces = { placeId, placeType ->
                        onSelectedPlaces.invoke(placeId, placeType)
                    },
                )
            }
        })


}

