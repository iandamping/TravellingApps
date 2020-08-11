package com.junemon.core.presentation

import android.content.Context
import android.os.Build
import android.view.DisplayCutout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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

    const val FILENAME = "Places"
    const val PHOTO_EXTENSION = ".jpg"

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

    /** Pad this view with the insets provided by the device cutout (i.e. notch) */
    @RequiresApi(Build.VERSION_CODES.P)
    fun View.padWithDisplayCutout() {

        /** Helper method that applies padding from cutout's safe insets */
        fun doPadding(cutout: DisplayCutout) = setPadding(
            cutout.safeInsetLeft,
            cutout.safeInsetTop,
            cutout.safeInsetRight,
            cutout.safeInsetBottom)

        // Apply padding using the display cutout designated "safe area"
        rootWindowInsets?.displayCutout?.let { doPadding(it) }

        // Set a listener for window insets since view.rootWindowInsets may not be ready yet
        setOnApplyWindowInsetsListener { _, insets ->
            insets.displayCutout?.let { doPadding(it) }
            insets
        }
    }

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