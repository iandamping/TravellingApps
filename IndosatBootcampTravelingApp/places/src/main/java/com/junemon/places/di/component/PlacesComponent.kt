package com.junemon.places.di.component

import com.junemon.travelingapps.di.module.PlaceModule
import com.junemon.travelingapps.feature.detail.DetailFragment
import com.junemon.travelingapps.feature.home.HomeFragment
import com.junemon.travelingapps.feature.pagination.PaginationFragment
import com.junemon.travelingapps.feature.search.SearchFragment
import com.junemon.travelingapps.feature.upload.UploadFragment
import com.junemon.core.di.scope.FeatureScope
import com.junemon.travelingapps.di.module.PlaceHomeModule
import com.junemon.travelingapps.di.ActivityComponent
import dagger.Component

/**
 * Created by Ian Damping on 15,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Component(
    modules = [PlaceModule::class, PlaceHomeModule::class], dependencies = [ActivityComponent::class]
)
@FeatureScope
interface PlacesComponent {

    fun inject(fragment: DetailFragment)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: PaginationFragment)

    fun inject(fragment: SearchFragment)

    fun inject(fragment: UploadFragment)

    @Component.Factory
    interface Factory {
        fun create(activityComponent: ActivityComponent): PlacesComponent
    }
}
