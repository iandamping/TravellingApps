package com.junemon.core.data.data.repository

import android.content.Intent
import com.junemon.core.data.data.datasource.ProfileRemoteDataSource
import com.junemon.core.domain.repository.ProfileRepository
import com.junemon.core.remote.util.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.domain.DataHelper
import com.junemon.model.domain.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRepositoryImpl @Inject constructor(private val remoteDataSource: ProfileRemoteDataSource) :
    ProfileRepository {

    override fun getUserProfile(): Flow<Results<AuthenticatedUserInfo>> {
        return flow {
            emitAll(remoteDataSource.getUserProfile().map { userResult ->
                if (userResult is DataHelper.RemoteSourceValue){
                    Results.Success(userResult.data)
                } else{
                    Results.Error(Exception("FirebaseAuth error"),null)
                }
            })
        }
    }

    override suspend fun initSignIn(): Intent {
        return remoteDataSource.initSignIn()
    }

    override suspend fun initLogout(onComplete: () -> Unit) {
        remoteDataSource.initLogout(onComplete)
    }
}