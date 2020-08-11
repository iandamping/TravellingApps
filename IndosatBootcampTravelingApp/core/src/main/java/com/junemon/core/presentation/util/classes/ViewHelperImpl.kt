package com.junemon.core.presentation.util.classes

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.junemon.core.presentation.util.interfaces.ViewHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ViewHelperImpl @Inject constructor() : ViewHelper {
    override fun View.visible(animated: Boolean = false) {
        visibility = View.VISIBLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && animated) {
            alpha = 0f
            animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null)
        }
    }

    override fun View.gone(animated: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && animated) {
            alpha = 1f
            animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        visibility = View.GONE
                    }
                })
        } else {
            visibility = View.GONE
        }
    }

    override fun ViewGroup.inflates(layout: Int): View {
        return LayoutInflater.from(context).inflate(layout, this, false)
    }
}