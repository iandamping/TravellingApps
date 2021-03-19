package com.junemon.travelingapps.feature.home.viewholders

import android.os.Build
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewRandomBinding

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeRandomViewHolder(
    private val binding: ItemRecyclerviewRandomBinding,
    private val loadImageHelper: LoadImageHelper
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PlaceCachePresentation) {
        binding.run {
            tvItemRandomPlaceName.text = data.placeName
            tvItemRandomPlaceDistrict.text = data.placeDistrict
            with(loadImageHelper){ivItemRandomPlaceImage.loadWithGlide(data.placePicture)}


            when {
                Build.VERSION.SDK_INT < 24 -> {
                    ViewCompat.setTransitionName(
                        cvItemRandomContainer,
                        data.placePicture
                    )
                }
                Build.VERSION.SDK_INT > 24 -> {
                    cvItemRandomContainer.transitionName = data.placePicture
                }
            }
        }
    }
}