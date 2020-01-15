package com.junemon.places.di.module

import androidx.lifecycle.ViewModel
import com.junemon.places.vm.PlaceViewModel
import com.junemon.travelingapps.presentation.di.factory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class PlaceConsumeModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlaceViewModel::class)
    abstract fun bindPlaceViewModel(gameViewModel: PlaceViewModel): ViewModel
}