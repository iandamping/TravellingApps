package com.junemon.uploader.utility.classes

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.junemon.uploader.utility.interfaces.PermissionHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PermissionUtilImpl @Inject constructor(private val context: Context) : PermissionHelper {

    override fun requestCameraPermissionsGranted(permissions: Array<String>) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestReadPermissionsGranted(permissions: Array<String>) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestGranted(permissions: Array<String>): Boolean = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun Fragment.onRequestingPermissionsResult(
        permissionCode: Int,
        requestCode: Int,
        grantResults: IntArray,
        permissionGranted: () -> Unit,
        permissionDenied: () -> Unit
    ) {
        when (requestCode) {
            permissionCode -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted.invoke()
                } else {
                    permissionDenied.invoke()
                }
            }
        }
    }

    override fun Fragment.requestingPermission(permissions: Array<String>, requestCode: Int) {
        this.requestPermissions(permissions, requestCode)
    }
}