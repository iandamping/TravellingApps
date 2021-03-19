package com.junemon.travelingapps.base

import android.content.Context
import android.content.Intent
import android.os.StrictMode
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import timber.log.Timber

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // dont use this, but i had to
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    protected fun baseNavigate(destination: NavDirections) =
        with(findNavController()) {
            currentDestination?.getAction(destination.actionId)
                ?.let { navigate(destination) }
        }

    protected fun baseNavigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) =
        with(findNavController()) {
            currentDestination?.getAction(destination.actionId)
                ?.let { navigate(destination, extraInfo) }
        }

    protected fun sharedImageIntent(intent: Intent) {
        startActivity(
            Intent.createChooser(
                intent,
                "Share Image"
            )
        )
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

    protected fun backPressed(action: () -> Unit) {
        val backCallback by lazy {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { // Handle the back button event
                    action.invoke()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backCallback
        )
    }
}