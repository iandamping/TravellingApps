package com.junemon.travelingapps.feature.home.slideradapter

import android.view.View
import com.junemon.model.presentation.PlaceCachePresentation

/**
 * Created by Ian Damping on 26,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface HomeSliderListener {

    fun onClickListener(view:View,data: PlaceCachePresentation)
}