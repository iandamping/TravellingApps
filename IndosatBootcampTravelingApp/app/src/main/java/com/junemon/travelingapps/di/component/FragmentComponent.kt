package com.junemon.travelingapps.di.component

import com.junemon.travelingapps.feature.detail.DetailFragment
import dagger.Subcomponent

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Subcomponent
interface FragmentComponent {
    fun inject(fragment: DetailFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): FragmentComponent
    }
}