package com.junemon.travelingapps.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junemon.core.domain.usecase.PlaceUseCase
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import javax.inject.Inject

class PlaceViewModel @Inject constructor(private val repository: PlaceUseCase) : ViewModel() {

    private val _setRunningForever: MutableLiveData<Boolean> = MutableLiveData()
    val setRunningForever: LiveData<Boolean>
        get() = _setRunningForever

    private val _searchItem: MutableLiveData<MutableList<PlaceCachePresentation>> =
        MutableLiveData()

    val searchItem: LiveData<MutableList<PlaceCachePresentation>>
        get() = _searchItem

    fun setSearchItem(data: MutableList<PlaceCachePresentation>) {
        this._searchItem.value = data
    }

    fun startRunningViewPager() {
        _setRunningForever.value = true
    }

    fun stopRunningViewPager() {
        _setRunningForever.value = false
    }

    fun getCache(): LiveData<List<PlaceCacheData>> = repository.getCache()

    fun getRemote(): LiveData<Results<List<PlaceCacheData>>> = repository.getRemote()

    fun getSelectedTypeCache(placeType: String): LiveData<List<PlaceCacheData>> =
        repository.getSelectedTypeCache(placeType)

    suspend fun delete() = repository.delete()
}