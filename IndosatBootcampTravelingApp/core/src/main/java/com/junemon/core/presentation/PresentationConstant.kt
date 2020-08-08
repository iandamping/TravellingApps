package com.junemon.core.presentation

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

    /** Combination of all flags required to put activity into immersive mode */
    const val FLAGS_FULLSCREEN =
        View.SYSTEM_UI_FLAG_LOW_PROFILE or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

    /** Milliseconds used for UI animations */
    const val ANIMATION_FAST_MILLIS = 50L
    const val ANIMATION_SLOW_MILLIS = 100L

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
}

inline val Context.layoutInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

internal fun ViewGroup.inflates(layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}