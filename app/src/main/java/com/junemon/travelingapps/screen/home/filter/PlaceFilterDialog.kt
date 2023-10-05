package com.junemon.travelingapps.screen.home.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.junemon.travelingapps.R

@Composable
fun PlaceFilterDialog(
    modifier: Modifier = Modifier,
    filterItem: FilterItem,
    selectedFilterItem: (FilterItem) -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .clickable {
                selectedFilterItem(filterItem)
            }
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        val (filterImage, filterText, selectedFilter, closeIcon) = createRefs()
        Image(
            modifier = Modifier
                .size(50.dp)
                .constrainAs(filterImage) {
                    top.linkTo(closeIcon.bottom)
                    start.linkTo(parent.start)
                    centerVerticallyTo(parent)
                },
            painter = painterResource(id = filterItem.filterIcon),
            contentDescription = null
        )

        Text(
            modifier = Modifier.constrainAs(filterText) {
                start.linkTo(filterImage.end, margin = 16.dp)
                centerVerticallyTo(parent)
            },
            text = filterItem.filterText,
            style = MaterialTheme.typography.subtitle2
        )

        Image(
            modifier = Modifier
                .size(30.dp)
                .constrainAs(selectedFilter) {
                    end.linkTo(parent.end)
                    centerVerticallyTo(parent)
                },
            painter = if (filterItem.isFilterSelected) painterResource(id = R.drawable.ic_check_circle_green_24dp) else painterResource(
                id = R.drawable.ic_check_circle_gray_24dp
            ),
            contentDescription = null
        )
    }

}