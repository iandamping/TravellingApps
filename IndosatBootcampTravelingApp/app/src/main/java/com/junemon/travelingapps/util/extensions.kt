package com.junemon.travelingapps.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.junemon.core.presentation.Event
import com.junemon.core.presentation.EventObserver
import com.junemon.travelingapps.util.classes.RecyclerHorizontalSnapHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Ian Damping on 01,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
fun RecyclerView.horizontalRecyclerviewInitializer() {
    layoutManager = LinearLayoutManager(
        this.context, LinearLayoutManager.HORIZONTAL,
        false
    )
    if (this.onFlingListener == null) {
        RecyclerHorizontalSnapHelper()
            .attachToRecyclerView(this)
    }
}

fun RecyclerView.verticalRecyclerviewInitializer() {
    layoutManager = LinearLayoutManager(
        this.context, LinearLayoutManager.VERTICAL,
        false
    )
}

fun RecyclerView.gridRecyclerviewInitializer(size: Int) {
    layoutManager = GridLayoutManager(
        this.context, size
    )
}

fun <T> Fragment.observeEvent(data: LiveData<Event<T>>, onBound: ((T) -> Unit)) {
    data.observe(this.viewLifecycleOwner, EventObserver {
        onBound.invoke(it)
    })
}

fun <T> Fragment.observe(data: LiveData<T>, onBound: ((T) -> Unit)) {
    data.observe(this.viewLifecycleOwner) {
        onBound.invoke(it)
    }
}

fun Fragment.clicks(
    view: View,
    duration: Long = 300,
    isUsingThrottle: Boolean = true,
    onBound: () -> Unit
) {
    if (isUsingThrottle) {
        view.clickListener().throttleFirst(duration).onEach {
            onBound.invoke()
        }.launchIn(this.viewLifecycleOwner.lifecycleScope)
    } else {
        view.setOnClickListener {
            onBound.invoke()
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
private fun View.clickListener(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        offer(Unit)
    }
    awaitClose { this@clickListener.setOnClickListener(null) }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - lastEmissionTime > windowDuration
        if (mayEmit) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}