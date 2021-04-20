package com.junemon.travelingapps.di.module

import com.junemon.travelingapps.feature.detail.DetailFragmentComponent
import com.junemon.travelingapps.feature.home.HomeComponent
import com.junemon.travelingapps.feature.pagination.PaginationComponent
import com.junemon.travelingapps.feature.search.SearchComponent
import dagger.Module

// This module tells a Component which are its subcomponents
@Module(
    subcomponents = [
        PaginationComponent::class,
        SearchComponent::class,
        DetailFragmentComponent::class,
        HomeComponent::class
    ]
)
class FragmentSubModule