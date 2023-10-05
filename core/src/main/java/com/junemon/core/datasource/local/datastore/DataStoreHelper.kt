package com.junemon.core.datasource.local.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 02,December,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface DataStoreHelper {

    suspend fun saveStringInDataStore(key: Preferences.Key<String>, value: String)

    fun getStringInDataStore(key: Preferences.Key<String>): Flow<String>

    suspend fun saveIntInDataStore(key: Preferences.Key<Int>, value: Int)

    fun getIntInDataStore(key: Preferences.Key<Int>): Flow<Int>

    suspend fun saveBooleanInDataStore(key: Preferences.Key<Boolean>, value: Boolean)

    fun getBooleanInDataStore(key: Preferences.Key<Boolean>): Flow<Boolean>
}