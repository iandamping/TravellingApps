package com.junemon.core.presentation.util.classes

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.junemon.core.presentation.util.interfaces.PermissionHelper
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PermissionUtilImpl @Inject constructor( private val context: Context) : PermissionHelper {

    override fun requestCameraPermissionsGranted(permissions:Array<String>) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestReadPermissionsGranted(permissions:Array<String>) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun Fragment.onRequestPermissionsResult(
        permissionCode: Int,
        requestCode: Int,
        grantResults: IntArray,
        permissionGranted: () -> Unit,
        permissionDenied:() -> Unit
    ) {
        when(requestCode){
            permissionCode ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permissionGranted.invoke()
                }else{
                    permissionDenied.invoke()
                }
            }
        }
    }

    override fun Fragment.requestingPermission(permissions: Array<String>, requestCode: Int) {
        this.requestPermissions(permissions, requestCode)
    }
}