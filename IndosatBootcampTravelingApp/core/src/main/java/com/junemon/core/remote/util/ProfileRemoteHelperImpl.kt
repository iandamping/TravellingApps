package com.junemon.core.remote.util

import android.app.Application
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.junemon.core.data.di.DefaultDispatcher
import com.junemon.core.data.di.IoDispatcher
import com.junemon.core.remote.util.firebaseuser.AuthenticatedUserInfo
import com.junemon.core.remote.util.firebaseuser.FirebaseUserInfo
import com.junemon.model.domain.DataHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRemoteHelperImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val context: Application,
    private val mFirebaseAuth: FirebaseAuth
) : ProfileRemoteHelper {

    private var isListening = false
    private var lastUid: String? = null

    // Channel that keeps track of User Authentication
    private val channel = ConflatedBroadcastChannel<DataHelper<AuthenticatedUserInfo>>()

    val listener: ((FirebaseAuth) -> Unit) = { auth ->
        Timber.d("Received a FirebaseAuth update")

        if (!channel.isClosedForSend) {
            channel.offer(DataHelper.RemoteSourceValue(FirebaseUserInfo(auth.currentUser)))
        } else {
            unregisterListener()
        }
    }

    override fun getUserProfile(): Flow<DataHelper<AuthenticatedUserInfo>> {
        if (!isListening) {
            mFirebaseAuth.addAuthStateListener(listener)
            isListening = true
        }
        return channel.asFlow()
    }

    override suspend fun initSignIn(): Intent {
        return withContext(ioDispatcher) {
            // this is mutable because FirebaseUI requires it to be mutable
            val providers = mutableListOf(
                AuthUI.IdpConfig.GoogleBuilder().setSignInOptions(
                    GoogleSignInOptions.Builder()
                        .requestId()
                        .requestEmail()
                        .build()
                ).build()
            )
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
        }
    }

    override suspend fun initLogout(onComplete: () -> Unit){
        withContext(ioDispatcher) {
            AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener { onComplete() }
        }
    }

    private fun unregisterListener() {
        mFirebaseAuth.removeAuthStateListener(listener)
    }
}