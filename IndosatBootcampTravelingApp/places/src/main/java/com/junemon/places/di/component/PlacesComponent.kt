package com.junemon.places.di.component

import com.junemon.places.di.module.PlaceConsumeModule
import com.junemon.places.feature.detail.DetailFragment
import com.junemon.places.feature.home.HomeFragment
import com.junemon.places.feature.pagination.PaginationFragment
import com.junemon.places.feature.search.SearchFragment
import com.junemon.places.feature.upload.UploadFragment
import com.junemon.core.di.scope.FeatureScope
import com.junemon.places.di.module.PlaceHomeModule
import com.junemon.travelingapps.di.ActivityComponent
import dagger.Component

/**
 * Created by Ian Damping on 15,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Component(
    modules = [PlaceConsumeModule::class,PlaceHomeModule::class], dependencies = [ActivityComponent::class]
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
