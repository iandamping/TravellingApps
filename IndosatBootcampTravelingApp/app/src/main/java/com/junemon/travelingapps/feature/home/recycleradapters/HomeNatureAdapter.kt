package com.junemon.travelingapps.feature.home.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewNaturePlaceBinding
import com.junemon.travelingapps.feature.home.viewholders.HomeNatureViewHolder
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.placeRvCallback
import javax.inject.Inject

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeNatureAdapter @Inject constructor(
    private val listener: HomeNatureAdapterListener,
    private val loadImageHelper: LoadImageHelper
) :
    ListAdapter<PlaceCachePresentation, HomeNatureViewHolder>(placeRvCallback) {

    interface HomeNatureAdapterListener {
        fun onNatureClicked(data: PlaceCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNatureViewHolder {
        return HomeNatureViewHolder(
            ItemRecyclerviewNaturePlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), loadImageHelper
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