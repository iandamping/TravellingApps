package com.junemon.travelingapps.feature.culture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.core.presentation.PresentationConstant.placeRvCallback
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemRecyclerviewCulturePlaceBinding

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeCultureAdapter(
    private val listener: HomeCultureAdapterListener
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
            )
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