package com.junemon.travelingapps.presentation

import androidx.recyclerview.widget.DiffUtil
import com.junemon.travelingapps.presentation.model.PlaceCachePresentation

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
object PresentationConstant {
    const val RequestSelectGalleryImage = 102
    const val RequestOpenCamera = 234

    val placeRvCallback = object : DiffUtil.ItemCallback<PlaceCachePresentation?>() {
        override fun areItemsTheSame(oldItem: PlaceCachePresentation, newItem: PlaceCachePresentation): Boolean {
            return oldItem.localPlaceID == newItem.localPlaceID
        }

        override fun areContentsTheSame(oldItem: PlaceCachePresentation, newItem: PlaceCachePresentation): Boolean {
            return oldItem == newItem
        }
    }
}