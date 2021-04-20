package com.junemon.travelingapps.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemSearchRecyclerviewBinding
import com.junemon.travelingapps.util.placePaginationRvCallback
import javax.inject.Inject

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchAdapter(
    private val listener: SearchAdapterListener,
    private val loadImageHelper: LoadImageHelper
) : ListAdapter<PlaceCachePresentation, SearchViewHolder>(placePaginationRvCallback) {

    interface SearchAdapterListener {
        fun onClicked(data: PlaceCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            loadImageHelper
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onClicked(data)
        }
    }
}