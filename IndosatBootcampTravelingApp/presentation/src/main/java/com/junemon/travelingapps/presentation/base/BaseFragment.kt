package com.junemon.travelingapps.presentation.base

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.junemon.travelingapps.presentation.R
import timber.log.Timber

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseFragment : Fragment() {
    private lateinit var alert: AlertDialog

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
            Timber.e(e)
        }
    }

    protected fun ilegallArgumenCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        }
    }

    protected fun ilegallStateCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalStateException) {
            // commonHelper.timberLogE(e.message)
        }
    }

    protected fun universalCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}