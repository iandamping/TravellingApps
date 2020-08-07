package com.junemon.core.remote.util.firebaseuser

import android.net.Uri

interface AuthenticatedUserInfo {

    fun isSignedIn(): Boolean

    fun getEmail(): String?

    fun getDisplayName(): String?

    fun getPhotoUrl(): Uri?

}