package com.junemon.travelingapps.feature.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.junemon.travelingapps.util.interfaces.ImageHelper
import com.junemon.travelingapps.util.interfaces.IntentHelper
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.interfaces.PermissionHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemPaginationRecyclerviewBinding
import com.junemon.travelingapps.util.placePaginationRvCallback
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PaginationAdapter (
    private val listener: PaginationAdapterrListener,
    private val permissionHelper: PermissionHelper,
    private val loadImageHelper: LoadImageHelper,
    private val imageHelper: ImageHelper,
    private val scope: CoroutineScope,
    private val intentHelper: IntentHelper,
):ListAdapter<PlaceCachePresentation, PaginationViewHolder>(placePaginationRvCallback) {

    interface PaginationAdapterrListener {
        fun onClicked(data: PlaceCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaginationViewHolder {
        return PaginationViewHolder(
            ItemPaginationRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            permissionHelper, loadImageHelper, imageHelper, intentHelper,scope)
    }
    override fun onBindViewHolder(holder: PaginationViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onClicked(data)
        }
    }
}