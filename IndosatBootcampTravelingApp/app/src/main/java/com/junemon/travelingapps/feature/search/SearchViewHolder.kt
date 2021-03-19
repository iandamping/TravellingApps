package com.junemon.travelingapps.feature.search

import android.os.Build
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemSearchRecyclerviewBinding

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchViewHolder(
    private val binding: ItemSearchRecyclerviewBinding,
    private val loadImageHelper: LoadImageHelper
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PlaceCachePresentation) {
        with(binding) {
            with(loadImageHelper) { ivItemPlaceImage.loadWithGlide(data.placePicture) }
            tvItemPlaceName.text = data.placeName
            tvItemPlaceDistrict.text = data.placeDistrict
            when {
                Build.VERSION.SDK_INT < 24 -> {
                    ViewCompat.setTransitionName(cvItemContainer, data.placePicture)
                }
                Build.VERSION.SDK_INT > 24 -> {
                    cvItemContainer.transitionName = data.placePicture
                }
            }
        }
    }
}