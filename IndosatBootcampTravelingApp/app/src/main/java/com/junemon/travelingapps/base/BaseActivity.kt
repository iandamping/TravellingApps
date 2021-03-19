package com.junemon.travelingapps.base

import androidx.lifecycle.lifecycleScope
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Job

/**
 * Created by Ian Damping on 15,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    private var baseJob: Job? = null

    protected fun consumeSuspend(func: suspend () -> Unit) {
        baseJob?.cancel()
        baseJob = lifecycleScope.launchWhenStarted {
            func.invoke()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        baseJob?.cancel()
    }
}