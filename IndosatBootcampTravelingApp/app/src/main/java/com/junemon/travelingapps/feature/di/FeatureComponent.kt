package com.junemon.travelingapps.feature.di

import com.junemon.travelingapps.di.module.PlaceConsumeModule
import com.junemon.travelingapps.feature.detail.DetailFragment
import com.junemon.travelingapps.feature.home.HomeFragment
import com.junemon.travelingapps.feature.pagination.PaginationFragment
import com.junemon.travelingapps.feature.search.SearchFragment
import com.junemon.travelingapps.feature.upload.UploadFragment
import dagger.Subcomponent

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Subcomponent(modules = [PlaceConsumeModule::class])
interface FeatureComponent {

    fun inject(fragment: DetailFragment)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: PaginationFragment)

    fun inject(fragment: SearchFragment)

    fun inject(fragment: UploadFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): FeatureComponent
    }
}