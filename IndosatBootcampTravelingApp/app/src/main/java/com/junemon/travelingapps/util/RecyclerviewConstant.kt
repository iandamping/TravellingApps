package com.junemon.travelingapps.util

import androidx.recyclerview.widget.DiffUtil
import com.junemon.model.presentation.PlaceCachePresentation

/**
 * Created by Ian Damping on 18,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
val placeRvCallback = object : DiffUtil.ItemCallback<PlaceCachePresentation?>() {
    override fun areItemsTheSame(
        oldItem: PlaceCachePresentation,
        newItem: PlaceCachePresentation
    ): Boolean {
        return oldItem.localPlaceID == newItem.localPlaceID
    }

    override fun areContentsTheSame(
        oldItem: PlaceCachePresentation,
        newItem: PlaceCachePresentation
    ): Boolean {
        return oldItem == newItem
    }
}

val placePaginationRvCallback = object : DiffUtil.ItemCallback<PlaceCachePresentation>() {
    override fun areItemsTheSame(
        oldItem: PlaceCachePresentation,
        newItem: PlaceCachePresentation
    ): Boolean {
        return oldItem.localPlaceID == newItem.localPlaceID
    }

    override fun areContentsTheSame(
        oldItem: PlaceCachePresentation,
        newItem: PlaceCachePresentation
    ): Boolean {
        return oldItem == newItem
    }
}