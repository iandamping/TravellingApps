package com.junemon.uploader.di.module

import com.junemon.core.di.module.placeInjector
import com.junemon.core.di.module.profileInjector
import com.junemon.uploader.vm.UploadViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.ScopeDSL

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
fun ScopeDSL.uploadViewModelInjector() {
    placeInjector()
    profileInjector()
    viewModel { UploadViewModel(get(), get()) }
}