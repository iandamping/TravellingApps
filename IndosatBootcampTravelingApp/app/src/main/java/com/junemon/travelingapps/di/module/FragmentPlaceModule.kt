package com.junemon.travelingapps.di.module

import com.junemon.travelingapps.feature.culture.FragmentCulture
import com.junemon.travelingapps.feature.nature.FragmentNature
import com.junemon.travelingapps.feature.religious.FragmentReligious
import com.junemon.travelingapps.feature.search.SearchFragment
import org.koin.dsl.module

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
val fragmentModule = module {
    scope<FragmentCulture> {
        placeViewModelInjector()
    }
    scope<FragmentNature> {
        placeViewModelInjector()
    }
    scope<FragmentReligious> {
        placeViewModelInjector()
    }
    scope<SearchFragment> {
        placeViewModelInjector()
    }
}