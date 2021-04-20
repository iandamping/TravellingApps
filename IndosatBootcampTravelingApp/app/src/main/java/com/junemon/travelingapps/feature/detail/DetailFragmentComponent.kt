package com.junemon.travelingapps.feature.detail

import dagger.Subcomponent

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Subcomponent
interface DetailFragmentComponent {
    // fun inject(fragment: DetailFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailFragmentComponent
    }
}