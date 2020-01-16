package com.junemon.core.presentation.util.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.junemon.core.presentation.util.interfaces.ViewHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ViewHelperImpl @Inject constructor() : ViewHelper {
    override fun View.visible() {
        this.visibility = View.VISIBLE
    }

    override fun View.gone() {
        this.visibility = View.GONE
    }

    override fun ViewGroup.inflates(layout: Int): View {
        return LayoutInflater.from(context).inflate(layout, this, false)
    }
}