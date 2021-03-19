package com.junemon.travelingapps.feature.home.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.placeRvCallback
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewCulturePlaceBinding
import com.junemon.travelingapps.feature.home.viewholders.HomeCultureViewHolder
import javax.inject.Inject

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeCultureAdapter @Inject constructor(
    private val listener: HomeCultureAdapterListener,
    private val loadImageHelper: LoadImageHelper
) :
    ListAdapter<PlaceCachePresentation, HomeCultureViewHolder>(placeRvCallback) {

    interface HomeCultureAdapterListener {
        fun onCultureClicked(data: PlaceCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCultureViewHolder {
        return HomeCultureViewHolder(
            ItemRecyclerviewCulturePlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            loadImageHelper
        )
    }

    override fun onBindViewHolder(holder: HomeCultureViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onCultureClicked(data)
        }
    }
}