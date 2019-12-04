package com.junemon.travelingapps.presentation.util.interfaces

import android.app.Activity

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PermissionHelperResult {

    fun getAllPermission(activity: Activity,isGranted:(Boolean) ->Unit)
}