package com.junemon.travelingapps.presentation.base

import androidx.fragment.app.Fragment
import com.ian.app.helper.interfaces.CommonHelperResult
import com.ian.app.helper.interfaces.LoadImageResult
import com.ian.app.helper.interfaces.ViewHelperResult
import com.ian.recyclerviewhelper.interfaces.RecyclerviewHelper
import com.junemon.travelingapps.presentation.util.interfaces.ImageHelperResult
import com.junemon.travelingapps.presentation.util.interfaces.PermissionHelperResult
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseFragment:Fragment(), KoinComponent {

    protected val recyclerViewHelper: RecyclerviewHelper by inject()
    protected val loadingImageHelper: LoadImageResult by inject()
    protected val viewHelper:ViewHelperResult by inject()
    protected val commonHelper: CommonHelperResult by inject()
    protected val imageHelper:ImageHelperResult by inject()
    protected val permissionHelper:PermissionHelperResult by inject()

    protected fun recyclerviewCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalArgumentException) {
            commonHelper.timberLogE(e.message)
        }
    }

    protected fun ilegallArgumenCatching(function: () -> Unit){
        try {
            function.invoke()
        } catch (e: IllegalArgumentException) {
            commonHelper.timberLogE(e.message)
        }
    }

    protected fun ilegallStateCatching(function: () -> Unit){
        try {
            function.invoke()
        } catch (e: IllegalStateException) {
            commonHelper.timberLogE(e.message)
        }
    }

    protected fun universalCatching(function: () -> Unit){
        try {
            function.invoke()
        } catch (e: Exception) {
            commonHelper.timberLogE(e.message)
        }
    }
}