package com.junemon.uploader.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Ian Damping on 20,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SharedViewModel: ViewModel() {
    private var _passedUri:MutableLiveData<String?> = MutableLiveData()
    val passedUri:LiveData<String?> = _passedUri

    fun setPassedUri(data: String?){
        _passedUri.value = data
    }
}