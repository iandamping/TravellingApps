package com.junemon.travelingapps.feature.home.viewholders

import android.os.Build
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewCulturePlaceBinding
import com.junemon.travelingapps.feature.home.recycleradapters.HomeCultureAdapter

/**
 * Created by Ian Damping on 31,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeCultureViewHolder(
    private val binding: ItemRecyclerviewCulturePlaceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PlaceCachePresentation) {
        binding.run {
            tvItemCulturePlaceName.text = data.placeName
            tvItemCulturePlaceDistrict.text = data.placeDistrict
            Glide.with(ivItemCulturePlaceImage).load(data.placePicture)
                .into(ivItemCulturePlaceImage)

            when {
                Build.VERSION.SDK_INT < 24 -> {
                    ViewCompat.setTransitionName(
                        cvItemCultureContainer,
                        data.placePicture
                    )
                }
                Build.VERSION.SDK_INT > 24 -> {
                    cvItemCultureContainer.transitionName = data.placePicture
                }
            }
        }
    }
}