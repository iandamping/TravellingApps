package com.junemon.travelingapps.di.module

import androidx.lifecycle.ViewModel
import com.junemon.core.di.scope.FeatureScope
import com.junemon.core.presentation.di.factory.ViewModelKey
import com.junemon.travelingapps.feature.detail.DetailFragment
import com.junemon.travelingapps.feature.culture.FragmentCulture
import com.junemon.travelingapps.feature.nature.FragmentNature
import com.junemon.travelingapps.feature.onboard.FragmentOnBoard
import com.junemon.travelingapps.feature.religious.FragmentReligious
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
    abstract fun contributeDetailFragment(): DetailFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeFragmentOnBoard(): FragmentOnBoard

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeFragmentNature(): FragmentNature

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeFragmentCulture(): FragmentCulture

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeFragmentReligious(): FragmentReligious

    @Binds
    @IntoMap
    @ViewModelKey(PlaceViewModel::class)
    abstract fun bindPlaceViewModel(gameViewModel: PlaceViewModel): ViewModel
}
