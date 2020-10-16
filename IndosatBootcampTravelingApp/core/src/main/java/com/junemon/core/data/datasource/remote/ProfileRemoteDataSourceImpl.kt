package com.junemon.core.data.datasource.remote

import android.content.Intent
import com.junemon.core.data.data.datasource.ProfileRemoteDataSource
import com.junemon.core.remote.util.ProfileRemoteHelper
import com.junemon.core.remote.util.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.domain.DataHelper
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRemoteDataSourceImpl(
    private val profileRemoteHelper: ProfileRemoteHelper
) : ProfileRemoteDataSource {
    override fun getUserProfile(): Flow<DataHelper<AuthenticatedUserInfo>> {
        return profileRemoteHelper.getUserProfile()
    }

    override suspend fun initSignIn(): Intent {
        return profileRemoteHelper.initSignIn()
    }

    override suspend fun initLogout(onComplete: () -> Unit){
        profileRemoteHelper.initLogout(onComplete)
    }
}