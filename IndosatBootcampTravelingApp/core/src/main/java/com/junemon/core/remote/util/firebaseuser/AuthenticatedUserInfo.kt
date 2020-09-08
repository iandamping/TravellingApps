package com.junemon.core.remote.util.firebaseuser


interface AuthenticatedUserInfo {

    fun getEmail(): String?

    fun getDisplayName(): String?

    fun getPhotoUrl(): String?

}