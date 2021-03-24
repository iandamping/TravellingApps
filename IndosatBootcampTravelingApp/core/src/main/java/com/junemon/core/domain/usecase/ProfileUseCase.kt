package com.junemon.core.domain.usecase

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.junemon.core.remote.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.domain.Results
import com.junemon.model.domain.UserResult

/**
 * Created by Ian Damping on 20,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ProfileUseCase {

    fun getUserProfile(): LiveData<UserResult<AuthenticatedUserInfo>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout(onComplete: () -> Unit)
}