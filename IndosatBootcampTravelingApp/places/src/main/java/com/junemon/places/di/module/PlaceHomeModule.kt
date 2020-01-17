package com.junemon.places.di.module

import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.places.feature.home.slideradapter.HomeSliderAdapter
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 17,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object PlaceHomeModule {

    @JvmStatic
    @Provides
    fun provideHomeSliderAdapter(viewHelper: ViewHelper,loadImageHelper: LoadImageHelper):HomeSliderAdapter{
        return HomeSliderAdapter(viewHelper, loadImageHelper)
    }
}