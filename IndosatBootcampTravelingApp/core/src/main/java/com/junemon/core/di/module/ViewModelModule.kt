package com.junemon.core.di.module

import androidx.lifecycle.ViewModelProvider
import com.junemon.core.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 16,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}