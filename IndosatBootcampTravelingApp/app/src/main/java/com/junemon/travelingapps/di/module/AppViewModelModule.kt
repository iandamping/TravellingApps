package com.junemon.travelingapps.di.module

import androidx.lifecycle.ViewModel
import com.junemon.core.di.factory.ViewModelKey
import com.junemon.travelingapps.vm.PlaceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface AppViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlaceViewModel::class)
    fun bindPlaceViewModel(gameViewModel: PlaceViewModel): ViewModel
}