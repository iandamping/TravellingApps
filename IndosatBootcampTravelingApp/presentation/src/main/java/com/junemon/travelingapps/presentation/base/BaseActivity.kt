package com.junemon.travelingapps.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.ian.app.helper.interfaces.CommonHelperResult
import com.ian.app.helper.interfaces.LoadImageResult
import com.ian.app.helper.interfaces.ViewHelperResult
import com.ian.recyclerviewhelper.interfaces.RecyclerviewHelper
import com.junemon.travelingapps.presentation.util.interfaces.ImageHelperResult
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseActivity : AppCompatActivity(), KoinComponent {
    protected val viewHelper: ViewHelperResult by inject()
    protected val recyclerViewHelper: RecyclerviewHelper by inject()
    protected val loadingImageHelper: LoadImageResult by inject()
    protected val commonHelper: CommonHelperResult by inject()
    protected val imageHelper: ImageHelperResult by inject()

    protected fun recyclerviewCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalArgumentException) {
            commonHelper.timberLogE(e.message)
        }
    }
}