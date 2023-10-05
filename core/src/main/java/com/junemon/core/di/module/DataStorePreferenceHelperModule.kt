package com.junemon.core.di.module

import com.junemon.core.datasource.local.datastore.DataStoreHelper
import com.junemon.core.datasource.local.datastore.DataStoreHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStorePreferenceHelperModule {

    @Binds
    fun bindDataStoreHelper(dataStoreHelper: DataStoreHelperImpl): DataStoreHelper
}