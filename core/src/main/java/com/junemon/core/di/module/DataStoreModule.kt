package com.junemon.core.di.module

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val dataStoreInit = " init data store"
    private val Context.dataStore by preferencesDataStore(
        name = dataStoreInit,
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        produceMigrations = { contexts ->
            listOf(SharedPreferencesMigration(contexts, dataStoreInit))
        }
    )

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context) = context.dataStore
}