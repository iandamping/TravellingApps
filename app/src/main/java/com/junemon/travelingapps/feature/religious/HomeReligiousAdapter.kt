package com.junemon.travelingapps.feature.religious

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.core.presentation.PresentationConstant.placeRvCallback
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewReligiusPlaceBinding

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeReligiousAdapter(
    private val listener: HomeReligiousAdapterListener
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
            )
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