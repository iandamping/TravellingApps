package com.junemon.travelingapps.feature.search

import dagger.BindsInstance
import dagger.Subcomponent

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Subcomponent
interface SearchComponent {
    // fun inject(fragment: SearchFragment)

    @Subcomponent.Factory
    interface Factory {
        fun provideListener(@BindsInstance listener: SearchAdapter.SearchAdapterListener): SearchComponent
    }
}