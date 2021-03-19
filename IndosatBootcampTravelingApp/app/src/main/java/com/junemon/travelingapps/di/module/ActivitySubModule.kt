package com.junemon.travelingapps.di.module

import com.junemon.travelingapps.di.component.ActivityComponent
import com.junemon.travelingapps.feature.pagination.PaginationComponent
import dagger.Module

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module(
    subcomponents = [
        ActivityComponent::class
    ]
)
class ActivitySubModule