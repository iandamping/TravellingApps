package com.junemon.travelingapps.feature.nature

import android.os.Build
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewNaturePlaceBinding

/**
 * Created by Ian Damping on 31,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeNatureViewHolder(
    private val binding: ItemRecyclerviewNaturePlaceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PlaceCachePresentation) {
        binding.run {
            tvItemNaturePlaceName.text = data.placeName
            tvItemNaturePlaceDistrict.text = data.placeDistrict
            Glide.with(ivItemNaturePlaceImage).load(data.placePicture).into(ivItemNaturePlaceImage)

            when {
                Build.VERSION.SDK_INT < 24 -> {
                    ViewCompat.setTransitionName(
                        cvItemNatureContainer,
                        data.placePicture
                    )
                }
                Build.VERSION.SDK_INT > 24 -> {
                    cvItemNatureContainer.transitionName = data.placePicture
                }
            }
        }
    }
}