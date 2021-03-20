package com.junemon.core.domain.usecase

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.junemon.core.domain.repository.ProfileRepository
import com.junemon.core.remote.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.domain.Results
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileUseCaseImpl @Inject constructor(private val repository: ProfileRepository):ProfileUseCase {

    override fun getUserProfile(): LiveData<Results<AuthenticatedUserInfo>> = repository.getUserProfile().asLiveData()

    override suspend fun initSignIn(): Intent = repository.initSignIn()

    override suspend fun initLogout(onComplete: () -> Unit) = repository.initLogout(onComplete)
}