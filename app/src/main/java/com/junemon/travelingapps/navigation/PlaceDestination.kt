package com.junemon.travelingapps.navigation

import com.junemon.travelingapps.navigation.PlaceDestination.ScreensNavigationConstant.DETAIL_SCREEN
import com.junemon.travelingapps.navigation.PlaceDestination.ScreensNavigationConstant.HOME_SCREEN

sealed class PlaceDestination{
    data class LoadHome(val name: String = HOME_SCREEN) : PlaceDestination()
    data class LoadDetail(val name: String = DETAIL_SCREEN) : PlaceDestination()


    private object ScreensNavigationConstant {
        const val HOME_SCREEN = "Home Screen"
        const val DETAIL_SCREEN = "Detail Screen"

    }
}
