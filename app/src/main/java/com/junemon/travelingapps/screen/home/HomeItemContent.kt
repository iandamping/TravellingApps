package com.junemon.travelingapps.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.junemon.core.domain.model.BorneoPlaces
import com.junemon.travelingapps.R

@Composable
fun HomeItemContent(
    modifier: Modifier = Modifier,
    place: BorneoPlaces,
    onSelectedPlace: (BorneoPlaces) -> Unit
) {
    Card(
        modifier = modifier
            .size(
                250.dp
            )
            .clickable {
                onSelectedPlace.invoke(place)
            }
            .fillMaxSize(),
        elevation = 8.dp,
    ) {
        ConstraintLayout(modifier = Modifier) {
            val bottomGuideline = createGuidelineFromBottom(0.25f)
            val (imageRef, nameRef) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(bottomGuideline)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(place.placePicture)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = stringResource(id = R.string.detail_image, place.placeName),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier
                    .constrainAs(nameRef) {
                        top.linkTo(bottomGuideline)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp), text = place.placeName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
        }

    }
}