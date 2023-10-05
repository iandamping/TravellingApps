package com.junemon.travelingapps.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.junemon.travelingapps.R

@Composable
fun HomeTitle(
    modifier: Modifier = Modifier,
    userSearch: String,
    onUserSearch: (String) -> Unit,
    isFilterISClicked: () -> Unit,
) {
    ConstraintLayout(modifier = modifier) {
        val imageIconGuideLine = createGuidelineFromStart(0.2f)
        val filterIconGuideLine = createGuidelineFromEnd(0.2f)
        val (titleRef, imageIconRef, iconFilterRef, searchRef) = createRefs()

        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .constrainAs(imageIconRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(imageIconGuideLine)
                    width = Dimension.fillToConstraints
                },
            painter = painterResource(id = R.drawable.travel),
            contentDescription = null
        )

        Text(
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(imageIconRef.top)
                bottom.linkTo(imageIconRef.bottom)
                start.linkTo(imageIconGuideLine)
                end.linkTo(filterIconGuideLine)
                centerHorizontallyTo(parent)
            },
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )

        Image(
            modifier = Modifier
                .clickable {
                    isFilterISClicked.invoke()
                }
                .constrainAs(iconFilterRef) {
                    top.linkTo(imageIconRef.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(imageIconRef.bottom)
                    height = Dimension.fillToConstraints
                },
            imageVector = Icons.Default.Sort,
            contentDescription = stringResource(R.string.filter_place)
        )


        OutlinedTextField(
            modifier = Modifier.constrainAs(searchRef) {
                top.linkTo(imageIconRef.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            value = userSearch,
            onValueChange = { query ->
                onUserSearch.invoke(query)
            }, placeholder = {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(R.string.search_place),
                        style = MaterialTheme.typography.body2
                    )
                }
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        onUserSearch.invoke("")
                    },
                    imageVector = Icons.Default.HighlightOff,
                    contentDescription = stringResource(R.string.clear_search)
                )
            },
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            singleLine = true
        )

    }
}