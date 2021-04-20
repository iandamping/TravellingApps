package com.junemon.travelingapps.base

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.junemon.core.R
import com.junemon.core.presentation.layoutInflater
import com.junemon.travelingapps.databinding.CustomLoadingBinding
import kotlinx.coroutines.Job

/**
 * Created by Ian Damping on 18,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseFragmentViewBinding<out VB : ViewBinding> : BaseFragment() {
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    private var baseJob: Job? = null
    private var _loadingBinding: CustomLoadingBinding? = null
    private val loadingBinding get() = _loadingBinding!!
    private lateinit var alert: AlertDialog

    protected fun navigate(destination: NavDirections) = baseNavigate(destination)

    protected fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) =
        baseNavigate(destination, extraInfo)

    protected fun navigateUp() =
        with(findNavController()) {
            navigateUp()
        }

    protected fun consumeSuspend(func: suspend () -> Unit) {
        baseJob?.cancel()
        baseJob = lifecycleScope.launchWhenStarted {
            func.invoke()
        }
    }

    abstract fun viewCreated()

    abstract fun activityCreated()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBaseDialog(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated()
        activityCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        baseJob?.cancel()
        _loadingBinding = null
    }

    private fun setBaseDialog(context: Context) {
        _loadingBinding = CustomLoadingBinding.inflate(context.layoutInflater)

        val dialogBuilder = AlertDialog.Builder(context).run {
            setView(loadingBinding.root)
        }

        alert = dialogBuilder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        with(loadingBinding) {
            lottieAnimation.enableMergePathsForKitKatAndAbove(true)
        }
    }

    protected fun setupExitEnterTransition() {
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_xsmall).toLong()
        }
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.motion_duration_xsmall).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_xsmall).toLong()
        }
    }

    fun setDialogShow(status: Boolean) {
        if (::alert.isInitialized) {
            if (status) {
                alert.dismiss()
            } else {
                alert.show()
            }
        }
    }

    protected fun onBackPressed(action: () -> Unit) = backPressed(action)

    protected fun shareImageIntent(intent: Intent) = sharedImageIntent(intent)
}
