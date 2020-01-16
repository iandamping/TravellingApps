package com.junemon.core.presentation.di

import androidx.lifecycle.ViewModelProvider
import com.junemon.core.presentation.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 16,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):ViewModelProvider.Factory
}