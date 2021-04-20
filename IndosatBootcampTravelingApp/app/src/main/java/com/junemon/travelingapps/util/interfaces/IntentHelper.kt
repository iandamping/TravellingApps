package com.junemon.travelingapps.util.interfaces

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.FragmentActivity

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface IntentHelper {

    fun intentOpenWebsite(activity: Activity, url: String)

    fun intentShareText(activity: FragmentActivity, text: String)

    suspend fun intentShareImageAndText(
        tittle: String?,
        message: String?,
        imageUrl: String?,
        intent: (Intent) -> Unit
    )
}