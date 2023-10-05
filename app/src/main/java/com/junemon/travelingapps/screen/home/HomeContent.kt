package com.junemon.travelingapps.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.junemon.travelingapps.R
import com.junemon.travelingapps.shimmer.ShimmerAnimation
import com.junemon.travelingapps.state.PlaceUiState
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    state: State<PlaceUiState>,
    userSearch: String,
    onSelectedPlaces: (Int, String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listGridState = rememberLazyGridState()

    ConstraintLayout(modifier = modifier) {
        val (contentRef, btnBackToTopRef, errorRef) = createRefs()

        if (state.value.isLoading) {
            LazyVerticalGrid(
                modifier = Modifier.constrainAs(contentRef) {
                    top.linkTo(parent.top, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(10) {
                    ShimmerAnimation {
                        HomeItemShimmer(brush = it)
                    }
                }
            }

        } else {
            when {
                state.value.data.isNotEmpty() -> {
                    LazyVerticalGrid(
                        modifier = Modifier.constrainAs(contentRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        state = listGridState
                    ) {
                        items(items = if (userSearch.isEmpty()) {
                            state.value.data
                        } else state.value.data.filter { filter ->
                            checkNotNull(
                                filter.placeName.lowercase(Locale.getDefault())
                                    .contains(userSearch)
                            )
                        }, key = { key -> key.localPlaceID!! }) { places ->
                            HomeItemContent(
                                place = places,
                                onSelectedPlace = { selectedItem ->
                                    onSelectedPlaces.invoke(
                                        selectedItem.localPlaceID ?: 0,
                                        selectedItem.placeType
                                    )
                                })
                        }

                    }


                    val gridShowScrollToTopButton by remember {
                        derivedStateOf {
                            listGridState.firstVisibleItemIndex > 0
                        }
                    }

                    AnimatedVisibility(
                        visible = gridShowScrollToTopButton,
                        modifier = Modifier.constrainAs(btnBackToTopRef) {
                            bottom.linkTo(parent.bottom, 12.dp)
                            centerHorizontallyTo(parent)
                        }) {
                        Button(shape = RoundedCornerShape(20.dp),
                            onClick = {
                                coroutineScope.launch {
                                    // Animate scroll to the first item
                                    listGridState.animateScrollToItem(index = 0)
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.ArrowUpward,
                                contentDescription = "back to top"
                            )
                            Text(text = "back to top")
                        }
                    }
                }

                state.value.errorMessage.isNotEmpty() -> {
                    Column(
                        modifier = Modifier.constrainAs(errorRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            centerVerticallyTo(parent)
                            centerHorizontallyTo(parent)
                        }, horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(id = R.drawable.ic_no_data),
                            contentDescription = stringResource(id = R.string.no_data_available)
                        )

                        Text(
                            text = stringResource(id = R.string.no_data_available),
                            style = MaterialTheme.typography.subtitle1,
                        )
                    }
                }
            }
        }
    }

}