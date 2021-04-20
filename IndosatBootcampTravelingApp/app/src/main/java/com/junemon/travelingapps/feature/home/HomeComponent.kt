package com.junemon.travelingapps.feature.home

import com.junemon.travelingapps.feature.home.recycleradapters.HomeCultureAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.HomeNatureAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.HomeRandomAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.HomeReligiousAdapter
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */

@Subcomponent
interface HomeComponent {
    fun inject(fragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory {
        fun provideListener(
            @BindsInstance cultuerListener: HomeCultureAdapter.HomeCultureAdapterListener,
            @BindsInstance natureListener: HomeNatureAdapter.HomeNatureAdapterListener,
            @BindsInstance randomListener: HomeRandomAdapter.HomeRandomAdapterListener,
            @BindsInstance reliListener: HomeReligiousAdapter.HomeReligiousAdapterListener
        ): HomeComponent
    }
}