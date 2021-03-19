package com.ian.junemon.foodiepedia.feature.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.model.Event

/**
 * Created by Ian Damping on 20,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SharedViewModel: BaseViewModel() {
    private var _passedUri:MutableLiveData<Event<String>> = MutableLiveData()
    val passedUri:LiveData<Event<String>> = _passedUri

    fun setPassedUri(data: String){
        _passedUri.value = Event(data)
    }
}