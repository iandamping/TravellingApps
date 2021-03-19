package com.junemon.uploader.utility.classes

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.junemon.uploader.base.adapter.BaseKotlinListAdapter
import com.junemon.uploader.utility.interfaces.RecyclerHelper
import javax.inject.Inject

class RecyclerHelperImpl @Inject constructor() :
    RecyclerHelper {

    override fun <T> RecyclerView.setUpVerticalListAdapter(
        items: List<T>,
        diffUtil: DiffUtil.ItemCallback<T>,
        layoutResId: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit,
        manager: RecyclerView.LayoutManager
    ): BaseKotlinListAdapter<T> {

        val mutableData:MutableSet<T> = mutableSetOf()
        mutableData.clear()
        items.forEach {
            mutableData.add(it)
        }

        return BaseKotlinListAdapter(
            layout = layoutResId,
            bindHolder = { bindHolder(it) },
            diffUtil = diffUtil,
            itemClicks = { itemClick() }).apply {
            layoutManager = manager
            adapter = this
            submitList(mutableData.toList())
            notifyDataSetChanged()
        }
    }

    override fun <T> RecyclerView.setUpVerticalGridAdapter(
        items: List<T>,
        diffUtil: DiffUtil.ItemCallback<T>,
        layoutResId: Int,
        gridSize: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit,
        manager: RecyclerView.LayoutManager
    ): BaseKotlinListAdapter<T> {

        val mutableData:MutableSet<T> = mutableSetOf()
        mutableData.clear()
        items.forEach {
            mutableData.add(it)
        }

        return BaseKotlinListAdapter(
            layout = layoutResId,
            bindHolder = { bindHolder(it) },
            diffUtil = diffUtil,
            itemClicks = { itemClick() }).apply {
            layoutManager = manager
            adapter = this
            submitList(mutableData.toList())
            notifyDataSetChanged()
        }
    }

    override fun <T> RecyclerView.setUpHorizontalListAdapter(
        items: List<T>,
        diffUtil: DiffUtil.ItemCallback<T>,
        layoutResId: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit,
        manager: RecyclerView.LayoutManager
    ): BaseKotlinListAdapter<T> {

        val mutableData:MutableSet<T> = mutableSetOf()
        mutableData.clear()
        items.forEach {
            mutableData.add(it)
        }

        if (this.onFlingListener == null) {
            RecyclerHorizontalSnapHelper()
                .attachToRecyclerView(this)
        }


        return BaseKotlinListAdapter(
            layout = layoutResId,
            bindHolder = { bindHolder(it) },
            diffUtil = diffUtil,
            itemClicks = { itemClick() }).apply {
            layoutManager = manager
            adapter = this
            submitList(mutableData.toList())
            notifyDataSetChanged()
        }
    }
}
