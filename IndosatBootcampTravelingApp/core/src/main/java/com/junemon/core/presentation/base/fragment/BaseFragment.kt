package com.junemon.core.presentation.base.fragment

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.junemon.core.databinding.CustomLoadingBinding
import com.junemon.core.presentation.layoutInflater
import dagger.android.support.DaggerFragment
import timber.log.Timber

/**
 * Created by Ian Damping on 05,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseFragment : DaggerFragment() {
    private var _binding: CustomLoadingBinding? = null
    private val binding get() = _binding!!
    private lateinit var alert: AlertDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBaseDialog(context)
        // dont use this, but i had to
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    private fun setBaseDialog(context: Context) {
        _binding = CustomLoadingBinding.inflate(context.layoutInflater)

        val dialogBuilder = AlertDialog.Builder(context).run {
            setView(binding.root)
        }

        alert = dialogBuilder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        binding.run {
            lottieAnimation.enableMergePathsForKitKatAndAbove(true)
        }
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
            Timber.e(e)
        }
    }

    protected fun universalCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    abstract fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    abstract fun viewCreated(view: View, savedInstanceState: Bundle?)

    abstract fun destroyView()

    abstract fun activityCreated()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activityCreated()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroyView()
        _binding = null
    }
}