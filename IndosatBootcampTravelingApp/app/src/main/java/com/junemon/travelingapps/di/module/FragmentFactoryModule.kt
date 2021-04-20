package com.junemon.travelingapps.di.module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.junemon.travelingapps.di.factory.DefaultFragmentFactory
import com.junemon.travelingapps.di.factory.FragmentKey
import com.junemon.travelingapps.feature.detail.DetailFragment
import com.junemon.travelingapps.feature.home.HomeFragment
import com.junemon.travelingapps.feature.pagination.PaginationFragment
import com.junemon.travelingapps.feature.search.SearchFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 20,April,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class FragmentFactoryModule {
    @Binds
    abstract fun bindFragmentFactory(factory: DefaultFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun getFragmentSplash(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DetailFragment::class)
    abstract fun contributeDetailFragment(fragment: DetailFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(PaginationFragment::class)
    abstract fun contributePaginationFragment(fragment: PaginationFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SearchFragment::class)
    abstract fun contributeSearchFragment(fragment: SearchFragment): Fragment
}