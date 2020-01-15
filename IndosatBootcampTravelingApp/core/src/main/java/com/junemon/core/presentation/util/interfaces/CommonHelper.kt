package com.junemon.travelingapps.presentation.util.interfaces

import android.content.Context
import android.widget.EditText

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface CommonHelper {

    fun Context.myToast(msg: String?)

    fun EditText.requestError(msg: String?)
}