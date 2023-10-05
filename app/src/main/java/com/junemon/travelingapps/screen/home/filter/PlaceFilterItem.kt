package com.junemon.travelingapps.screen.home.filter

import com.junemon.travelingapps.R

object PlaceFilterItem {
    const val FILTER_0 = "Semua"
    const val FILTER_1 = "Wisata Alam"
    const val FILTER_2 = "Wisata Budaya"
    const val FILTER_3 = "Wisata Religi"
    const val FILTER_4 = "Tempat Favorite"

    val DEFAULT_FILTER_ITEM = listOf(
        FilterItem(
            filterIcon = R.drawable.nature,
            filterText = FILTER_0,
            isFilterSelected = true
        ),
        FilterItem(
            filterIcon = R.drawable.forest,
            filterText = FILTER_1,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.unity,
            filterText = FILTER_2,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.religion,
            filterText = FILTER_3,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.savannah,
            filterText = FILTER_4,
            isFilterSelected = false
        ),
    )

    val FILTER_ITEM = listOf(
        FilterItem(
            filterIcon = R.drawable.nature,
            filterText = FILTER_0,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.forest,
            filterText = FILTER_1,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.unity,
            filterText = FILTER_2,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.religion,
            filterText = FILTER_3,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.savannah,
            filterText = FILTER_4,
            isFilterSelected = false
        ),
    )

    fun configureFilterItem(selectedFilter: String): List<FilterItem> {
        return if (selectedFilter.isEmpty()) {
            DEFAULT_FILTER_ITEM
        } else {
            FILTER_ITEM.map { it.setIsSelected(selectedFilter) }
        }
    }

    private fun FilterItem.setIsSelected(selectedFilter: String): FilterItem =
        when (selectedFilter) {
            this.filterText -> FilterItem(
                filterIcon = this.filterIcon,
                filterText = this.filterText,
                isFilterSelected = true
            )

            else -> FilterItem(
                filterIcon = this.filterIcon,
                filterText = this.filterText,
                isFilterSelected = false
            )
        }

}

