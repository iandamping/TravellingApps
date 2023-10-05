package com.junemon.travelingapps.screen.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.junemon.travelingapps.R
import com.junemon.travelingapps.screen.detail.ParallaxEffectConstant.headerHeight

@Composable
fun DetailHeader(
    modifier: Modifier = Modifier,
    placePicture: String,
    scroll: ScrollState,
    headerHeightPx: Float
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(headerHeight)
        .graphicsLayer {
            translationY = -scroll.value.toFloat() / 2f // Parallax effect
            alpha = (-1f / headerHeightPx) * scroll.value + 1
        }
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 70.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(placePicture)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_placeholder),
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0x80FFFFFF)),
                        startY = 3 * headerHeightPx / 4 // Gradient applied to wrap the title only
                    )
                )
        )
    }
}