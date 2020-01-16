package com.junemon.places.di

import com.junemon.places.di.component.DaggerPlacesComponent
import com.junemon.places.di.component.PlacesComponent
import com.junemon.travelingapps.activity.MainActivity
import com.junemon.core.presentation.base.BaseFragment

/**
 * Created by Ian Damping on 16,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun BaseFragment.sharedPlaceComponent(): PlacesComponent =
    DaggerPlacesComponent.factory().create((this.activity as MainActivity).coreComponent)
