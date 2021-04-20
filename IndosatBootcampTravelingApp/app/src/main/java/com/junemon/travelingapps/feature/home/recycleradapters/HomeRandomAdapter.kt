package com.junemon.travelingapps.feature.home.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewRandomBinding
import com.junemon.travelingapps.feature.home.viewholders.HomeRandomViewHolder
import com.junemon.travelingapps.util.placeRvCallback
import javax.inject.Inject

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeRandomAdapter(
    private val listener: HomeRandomAdapterListener,
    private val loadImageHelper: LoadImageHelper
) : ListAdapter<PlaceCachePresentation, HomeRandomViewHolder>(placeRvCallback) {

    interface HomeRandomAdapterListener {
        fun onRandomClicked(data: PlaceCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRandomViewHolder {
        return HomeRandomViewHolder(
            ItemRecyclerviewRandomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),loadImageHelper
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