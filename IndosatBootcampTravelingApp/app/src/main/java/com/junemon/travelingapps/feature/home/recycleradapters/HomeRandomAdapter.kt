package com.junemon.travelingapps.feature.home.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.core.presentation.PresentationConstant
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewRandomBinding
import com.junemon.travelingapps.feature.home.viewholders.HomeRandomViewHolder

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeRandomAdapter(
    private val listener: HomeRandomAdapterListener
) : ListAdapter<PlaceCachePresentation, HomeRandomViewHolder>(PresentationConstant.placeRvCallback) {

    interface HomeRandomAdapterListener {
        fun onRandomClicked(data: PlaceCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRandomViewHolder {
        return HomeRandomViewHolder(
            ItemRecyclerviewRandomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeRandomViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onRandomClicked(data)
        }
    }
}