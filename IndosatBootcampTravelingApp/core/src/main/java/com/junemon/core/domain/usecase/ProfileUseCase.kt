package com.junemon.core.domain.usecase

import android.content.Intent
import com.junemon.core.domain.repository.ProfileRepository
import com.junemon.core.remote.util.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.domain.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileUseCase @Inject constructor(private val repository: ProfileRepository) {

    fun getUserProfile(): Flow<Results<AuthenticatedUserInfo>> = repository.getUserProfile()

    suspend fun initSignIn(): Intent = repository.initSignIn()

    suspend fun initLogout(onComplete: () -> Unit) = repository.initLogout(onComplete)
}