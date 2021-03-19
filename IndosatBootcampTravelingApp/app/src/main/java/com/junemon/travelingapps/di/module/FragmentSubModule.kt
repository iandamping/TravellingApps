package com.junemon.travelingapps.di.module

import com.junemon.travelingapps.di.component.FragmentComponent
import com.junemon.travelingapps.feature.home.HomeComponent
import com.junemon.travelingapps.feature.pagination.PaginationComponent
import com.junemon.travelingapps.feature.search.SearchComponent
import dagger.Module

// This module tells a Component which are its subcomponents
@Module(
    subcomponents = [
        PaginationComponent::class,
        SearchComponent::class,
        FragmentComponent::class,
        HomeComponent::class
    ]
)
class FragmentSubModule