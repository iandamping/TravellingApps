package com.junemon.travelingapps.presentation.base

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.ian.app.helper.interfaces.CommonHelperResult
import com.ian.app.helper.interfaces.LoadImageResult
import com.ian.app.helper.interfaces.ViewHelperResult
import com.ian.recyclerviewhelper.interfaces.RecyclerviewHelper
import com.junemon.travelingapps.presentation.R
import com.junemon.travelingapps.presentation.util.interfaces.ImageHelperResult
import com.junemon.travelingapps.presentation.util.interfaces.IntentHelperResult
import com.junemon.travelingapps.presentation.util.interfaces.PermissionHelperResult
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseFragment : Fragment(), KoinComponent {
    private lateinit var alert: AlertDialog
    protected val recyclerViewHelper: RecyclerviewHelper by inject()
    protected val loadingImageHelper: LoadImageResult by inject()
    protected val viewHelper: ViewHelperResult by inject()
    protected val commonHelper: CommonHelperResult by inject()
    protected val imageHelper: ImageHelperResult by inject()
    protected val permissionHelper: PermissionHelperResult by inject()
    protected val intentHelper: IntentHelperResult by inject()

    protected fun setBaseDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_loading, null)
        dialogBuilder.setView(dialogView)
        alert = dialogBuilder.create()
        val lottieAnim = dialogView.findViewById<LottieAnimationView>(R.id.lottieAnimation)
        lottieAnim.enableMergePathsForKitKatAndAbove(true)
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(false)
    }

    protected fun setDialogShow(status: Boolean) {
        if (status) {
            alert.dismiss()
        } else {
            alert.show()
        }
    }

    protected fun recyclerviewCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalArgumentException) {
            commonHelper.timberLogE(e.message)
        }
    }

    protected fun ilegallArgumenCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalArgumentException) {
            commonHelper.timberLogE(e.message)
        }
    }

    protected fun ilegallStateCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalStateException) {
            commonHelper.timberLogE(e.message)
        }
    }

    protected fun universalCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: Exception) {
            commonHelper.timberLogE(e.message)
        }
    }
}