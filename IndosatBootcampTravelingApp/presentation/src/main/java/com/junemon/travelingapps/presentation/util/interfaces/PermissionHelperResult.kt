package com.junemon.travelingapps.presentation.util.interfaces

import androidx.fragment.app.FragmentActivity

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PermissionHelperResult {

    fun getAllPermission(activity: FragmentActivity, isGranted: (Boolean) -> Unit)
}