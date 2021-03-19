package com.junemon.travelingapps.di.module

import androidx.lifecycle.ViewModel
import com.junemon.core.di.scope.FeatureScope
import com.junemon.core.di.factory.ViewModelKey
import com.junemon.travelingapps.feature.detail.DetailFragment
import com.junemon.travelingapps.feature.home.HomeFragment
import com.junemon.travelingapps.feature.pagination.PaginationFragment
import com.junemon.travelingapps.feature.search.SearchFragment
import com.junemon.travelingapps.vm.PlaceViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class PlaceModule {

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributePaginationFragment(): PaginationFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(PlaceViewModel::class)
    abstract fun bindPlaceViewModel(gameViewModel: PlaceViewModel): ViewModel
}
