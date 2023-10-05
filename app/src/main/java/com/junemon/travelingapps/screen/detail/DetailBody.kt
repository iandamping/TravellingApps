package com.junemon.travelingapps.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.junemon.travelingapps.state.FilteredPlaceUiState
import com.junemon.travelingapps.R
import com.junemon.travelingapps.screen.detail.ParallaxEffectConstant.headerHeight
import com.junemon.travelingapps.screen.detail.ParallaxEffectConstant.paddingMedium

@Composable
fun DetailBody(
    modifier: Modifier = Modifier,
    uiStateFiltered: FilteredPlaceUiState,
    scroll: ScrollState,
    placeId: Int,
    placeCity: String,
    placeAddress: String,
    placeDistrict: String,
    placeDetail: String,
    isItBookmarked: Boolean,
    onBookMarkPressed: () -> Unit,
    onSelectedSimilarPlaces: (Int, String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))
        Spacer(Modifier.height(paddingMedium))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Kota",
                style = MaterialTheme.typography.h5.copy(
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold
                )
            )

            IconButton(
                onClick = {
                    onBookMarkPressed.invoke()
                },
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = if (!isItBookmarked) Icons.Default.BookmarkBorder else Icons.Default.Bookmark,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }

        Text(
            text = "$placeCity - $placeDistrict",
            style = MaterialTheme.typography.h6.copy(
                textAlign = TextAlign.Justify
            )
        )

        Spacer(Modifier.height(paddingMedium))
        Text(
            text = "Alamat",
            style = MaterialTheme.typography.h6.copy(
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = placeAddress,
            style = MaterialTheme.typography.subtitle1.copy(
                textAlign = TextAlign.Justify
            )
        )

        Spacer(Modifier.height(paddingMedium))
        Text(
            text = "Keterangan",
            style = MaterialTheme.typography.h6.copy(
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = placeDetail,
            style = MaterialTheme.typography.body1.copy(
                textAlign = TextAlign.Justify
            )
        )
        Spacer(Modifier.height(paddingMedium))



        when {
            uiStateFiltered.data.isNotEmpty() -> {
                Text(
                    text = stringResource(R.string.similar_place),
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.height(paddingMedium))


                if (uiStateFiltered.data.size > 1) {
                    if (placeId != uiStateFiltered.data[0].localPlaceID) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectedSimilarPlaces.invoke(
                                        uiStateFiltered.data[0].localPlaceID ?: 0,
                                        uiStateFiltered.data[0].placeType
                                    )
                                }, elevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    modifier = Modifier.size(100.dp),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(uiStateFiltered.data[0].placePicture)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                                    contentDescription = stringResource(
                                        id = R.string.detail_image,
                                        uiStateFiltered.data[0].placeName
                                    ),
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = uiStateFiltered.data[0].placeName,
                                    style = MaterialTheme.typography.subtitle1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(paddingMedium))
                    if (placeId != uiStateFiltered.data[1].localPlaceID) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable {
                                    onSelectedSimilarPlaces.invoke(
                                        uiStateFiltered.data[1].localPlaceID ?: 0,
                                        uiStateFiltered.data[1].placeType
                                    )
                                },
                            elevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    modifier = Modifier.size(100.dp),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(uiStateFiltered.data[1].placePicture)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                                    contentDescription = stringResource(
                                        id = R.string.detail_image,
                                        uiStateFiltered.data[1].placeName
                                    ),
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = uiStateFiltered.data[1].placeName,
                                    style = MaterialTheme.typography.subtitle1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }


                } else {
                    if (placeId != uiStateFiltered.data[0].localPlaceID) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable {
                                    onSelectedSimilarPlaces.invoke(
                                        uiStateFiltered.data[0].localPlaceID ?: 0,
                                        uiStateFiltered.data[0].placeType
                                    )
                                },
                            elevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    modifier = Modifier.size(100.dp),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(uiStateFiltered.data[0].placePicture)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                                    contentDescription = stringResource(
                                        id = R.string.detail_image,
                                        uiStateFiltered.data[0].placeName
                                    ),
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = uiStateFiltered.data[0].placeName,
                                    style = MaterialTheme.typography.subtitle1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }

            uiStateFiltered.errorMessage.isNotEmpty() -> {
                Text(
                    text = stringResource(R.string.similar_place),
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.height(paddingMedium))

                Column(
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
}