package com.junemon.core.presentation.util.classes

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.junemon.core.presentation.util.interfaces.CommonHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class CommonHelperImpl @Inject constructor() : CommonHelper {

    override fun Context.myToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun EditText.requestError(msg: String?) {
        if (this.text.isNullOrEmpty()) {
            this.requestFocus()
            this.error = msg
        }
    }
}