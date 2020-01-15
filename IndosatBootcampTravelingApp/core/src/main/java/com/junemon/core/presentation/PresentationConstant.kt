package com.junemon.travelingapps.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.junemon.model.presentation.PlaceCachePresentation

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

    val placePaginationRvCallback = object : DiffUtil.ItemCallback<PlaceCachePresentation>() {
        override fun areItemsTheSame(oldItem: PlaceCachePresentation, newItem: PlaceCachePresentation): Boolean {
            return oldItem.localPlaceID == newItem.localPlaceID
        }

        override fun areContentsTheSame(oldItem: PlaceCachePresentation, newItem: PlaceCachePresentation): Boolean {
            return oldItem == newItem
        }
    }
}

inline val Context.layoutInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

internal fun ViewGroup.inflates(layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}