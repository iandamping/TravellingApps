package com.junemon.travelingapps.feature.home.viewholders

import android.os.Build
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewReligiusPlaceBinding
import com.junemon.travelingapps.util.interfaces.LoadImageHelper

/**
 * Created by Ian Damping on 31,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeReligiousViewHolder(
    private val binding: ItemRecyclerviewReligiusPlaceBinding,
    private val loadImageHelper: LoadImageHelper
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PlaceCachePresentation) {
        binding.run {
            tvItemReligiusPlaceName.text = data.placeName
            tvItemReligiusPlaceDistrict.text = data.placeDistrict
            with(loadImageHelper) { ivItemReligiusPlaceImage.loadWithGlide(data.placePicture) }
            when {
                Build.VERSION.SDK_INT < 24 -> {
                    ViewCompat.setTransitionName(
                        cvItemReligiusContainer,
                        data.placePicture
                    )
                }
                Build.VERSION.SDK_INT > 24 -> {
                    cvItemReligiusContainer.transitionName = data.placePicture
                }
            }
        }
    }
}