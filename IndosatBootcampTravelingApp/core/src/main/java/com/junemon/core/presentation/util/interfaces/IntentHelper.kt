package com.junemon.core.presentation.util.interfaces

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface IntentHelper {

    fun intentOpenWebsite(activity: Activity, url: String)

    fun intentShareText(activity: FragmentActivity, text: String)

    fun intentShareImageAndText(viewControllerContext: Context,tittle: String?, message: String?, imageUrl: String?)
}