package com.junemon.travelingapps.feature.home.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.placeRvCallback
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewReligiusPlaceBinding
import com.junemon.travelingapps.feature.home.viewholders.HomeReligiousViewHolder
import javax.inject.Inject

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeReligiousAdapter  @Inject constructor(
    private val listener: HomeReligiousAdapterListener,
    private val loadImageHelper: LoadImageHelper
) : ListAdapter<PlaceCachePresentation, HomeReligiousViewHolder>(placeRvCallback) {

    interface HomeReligiousAdapterListener {
        fun onReligiousClicked(data: PlaceCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeReligiousViewHolder {
        return HomeReligiousViewHolder(
            ItemRecyclerviewReligiusPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),loadImageHelper
        )
    }

    override fun onBindViewHolder(holder: HomeReligiousViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onReligiousClicked(data)
        }
    }

}