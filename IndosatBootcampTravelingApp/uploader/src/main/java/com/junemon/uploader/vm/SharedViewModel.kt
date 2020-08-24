package com.junemon.uploader.vm

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Ian Damping on 20,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SharedViewModel: ViewModel() {
    private var _passedUri:MutableLiveData<Uri?> = MutableLiveData()
    val passedUri:LiveData<Uri?> = _passedUri

    fun setPassedUri(uri: Uri?){
        _passedUri.value = uri
    }
}