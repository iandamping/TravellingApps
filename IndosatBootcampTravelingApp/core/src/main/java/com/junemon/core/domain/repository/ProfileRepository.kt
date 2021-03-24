package com.junemon.core.domain.repository

import android.content.Intent
import com.junemon.core.remote.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.domain.Results
import com.junemon.model.domain.UserResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ProfileRepository {
    fun getUserProfile(): Flow<UserResult<AuthenticatedUserInfo>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout(onComplete: () -> Unit)
}