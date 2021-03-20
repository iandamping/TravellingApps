package com.junemon.core.data.data.datasource

import android.content.Intent
import com.junemon.core.remote.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.domain.DataHelper
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ProfileRemoteDataSource {

    fun getUserProfile(): Flow<DataHelper<AuthenticatedUserInfo>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout(onComplete: () -> Unit)
}