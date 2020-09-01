package com.junemon.travelingapps.feature.home.viewholders

import android.os.Build
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewReligiusPlaceBinding

/**
 * Created by Ian Damping on 31,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeReligiousViewHolder(
    private val binding: ItemRecyclerviewReligiusPlaceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PlaceCachePresentation) {
        binding.run {
            tvItemReligiusPlaceName.text = data.placeName
            tvItemReligiusPlaceDistrict.text = data.placeDistrict
            Glide.with(ivItemReligiusPlaceImage).load(data.placePicture)
                .into(ivItemReligiusPlaceImage)

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