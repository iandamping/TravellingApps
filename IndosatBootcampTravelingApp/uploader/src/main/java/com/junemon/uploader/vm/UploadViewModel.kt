package com.junemon.uploader.vm

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.junemon.core.domain.usecase.PlaceUseCase
import com.junemon.core.domain.usecase.ProfileUseCase
import com.junemon.core.remote.util.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results

/**
 * Created by Ian Damping on 19,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadViewModel (
    private val repository: PlaceUseCase,
    private val profileRepo: ProfileUseCase
) : ViewModel() {

    fun uploadFirebaseData(
        data: PlaceRemoteData,
        imageUri: Uri?,
        success: (Boolean) -> Unit,
        failed: (Boolean, Throwable) -> Unit
    ) = repository.uploadFirebaseData(data, imageUri, success, failed)

    fun getUserProfile(): LiveData<Results<AuthenticatedUserInfo>> = profileRepo.getUserProfile()

    suspend fun initSignIn(): Intent = profileRepo.initSignIn()

    suspend fun initLogout(onComplete: () -> Unit) = profileRepo.initLogout(onComplete)
}