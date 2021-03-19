package com.junemon.uploader.utility.classes

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerHorizontalSnapHelper : LinearSnapHelper() {
    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(@NonNull layoutManager: RecyclerView.LayoutManager, @NonNull targetView: View): IntArray {
        val out = IntArray(2)

        if (layoutManager.canScrollHorizontally()) {
            out[0] = getHorizontalHelper(layoutManager)?.let { distanceToStart(targetView, it) }!!
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = getVerticalHelper(layoutManager)?.let { distanceToStart(targetView, it) }!!
        } else {
            out[1] = 0
        }
        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {

        return if (layoutManager is LinearLayoutManager) {

            if (layoutManager.canScrollHorizontally()) {
                getHorizontalHelper(layoutManager)?.let { getStartView(layoutManager, it) }
            } else {
                getVerticalHelper(layoutManager)?.let { getStartView(layoutManager, it) }
            }
        } else super.findSnapView(layoutManager)
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        return helper.getDecoratedStart(targetView) - helper.startAfterPadding
    }

    private fun getStartView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? {

        if (layoutManager is LinearLayoutManager) {
            val firstChild = layoutManager.findFirstVisibleItemPosition()

            val isLastItem = layoutManager
                .findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1

            if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
                return null
            }

            val child = layoutManager.findViewByPosition(firstChild)

            return if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2 && helper.getDecoratedEnd(
                    child
                ) > 0
            ) {
                child
            } else {
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    null
                } else {
                    layoutManager.findViewByPosition(firstChild + 1)
                }
            }
        }

        return super.findSnapView(layoutManager)
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper
    }
}
