package com.junemon.travelingapps.di

import com.ian.app.helper.di.commonHelperModule
import com.junemon.travelingapps.PlaceViewModel
import com.junemon.travelingapps.data.di.dataSourceModule
import com.junemon.travelingapps.data.di.databaseModule
import com.junemon.travelingapps.data.di.repositoryModules
import com.junemon.travelingapps.presentation.di.presentationModule
import com.junemon.travellingapps.domain.usecase.PlaceUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
fun injectData() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(dataSourceModule, repositoryModules, useCaseModule, commonHelperModule,presentationModule,viewmodelModule, databaseModule))
}
private val viewmodelModule = module {
    viewModel { PlaceViewModel(get()) }
}

private val useCaseModule = module {
    factory { PlaceUseCase(get()) }
}

