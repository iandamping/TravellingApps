package com.junemon.travelingapps.feature.nature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.core.presentation.PresentationConstant
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewNaturePlaceBinding

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeNatureAdapter(
    private val listener: HomeNatureAdapterListener
) :
    ListAdapter<PlaceCachePresentation, HomeNatureViewHolder>(PresentationConstant.placeRvCallback) {

    interface HomeNatureAdapterListener {
        fun onNatureClicked(data: PlaceCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNatureViewHolder {
        return HomeNatureViewHolder(
            ItemRecyclerviewNaturePlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeNatureViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onNatureClicked(data)
        }
    }
}