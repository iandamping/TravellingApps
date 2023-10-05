package com.junemon.travelingapps.screen.home.filter

import androidx.annotation.DrawableRes

data class FilterItem(
    @DrawableRes val filterIcon: Int,
    val filterText: String,
    var isFilterSelected: Boolean
)
