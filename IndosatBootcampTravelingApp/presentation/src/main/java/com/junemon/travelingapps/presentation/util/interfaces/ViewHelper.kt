package com.junemon.travelingapps.presentation.util.interfaces

import android.view.View
import android.view.ViewGroup

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ViewHelper {

    fun View.visible()

    fun View.gone()

    fun ViewGroup.inflates(layout: Int): View
}