package com.junemon.daggerinyourface.presentation.base.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.junemon.travelingapps.presentation.base.adapter.BaseListAdapter

/**
 *
Created by Ian Damping on 30/07/2019.
Github = https://github.com/iandamping
 */
class BaseKotlinListAdapter<T>(
    layout: Int,
    private val bindHolder: View.(T) -> Unit,
    diffUtil: DiffUtil.ItemCallback<T>,
    private val itemClicks: T.() -> Unit
) : BaseListAdapter<T>(layout, diffUtil, itemClicks) {

    override fun onBindViewHolder(holder: MyListWithSliderViewHolder, position: Int) {
        val data = getItem(position)
        holder.itemView.bindHolder(data)
        holder.itemView.setOnClickListener { itemClicks(data) }
    }
}
