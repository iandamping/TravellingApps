package com.junemon.travelingapps.feature.pagination

import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface PaginationComponent {

    // fun inject(fragment: PaginationFragment)

    @Subcomponent.Factory
    interface Factory {
        fun provideListener(@BindsInstance listener: PaginationAdapter.PaginationAdapterrListener): PaginationComponent
    }
}