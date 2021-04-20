package com.junemon.travelingapps.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.junemon.core.domain.usecase.PlaceUseCase
import com.junemon.core.presentation.Event
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import javax.inject.Inject

class PlaceViewModel @Inject constructor(private val repository: PlaceUseCase) : ViewModel() {

    private val _navigateEvent: MutableLiveData<Event<NavDirections>> = MutableLiveData()
    val navigateEvent: LiveData<Event<NavDirections>> = _navigateEvent

    fun setNavigate(direction: NavDirections) {
        _navigateEvent.value = Event(direction)
    }

    private val _searchItem: MutableLiveData<List<PlaceCachePresentation>> =
        MutableLiveData()

    val searchItem: LiveData<List<PlaceCachePresentation>>
        get() = _searchItem

    fun setSearchItem(data: List<PlaceCachePresentation>) {
        this._searchItem.value = data
    }

    fun getCache(): LiveData<List<PlaceCacheData>> = repository.getCache()

    fun getRemote(): LiveData<Results<List<PlaceCacheData>>> = repository.getRemote()

    fun getSelectedTypeCache(placeType: String): LiveData<List<PlaceCacheData>> =
        repository.getSelectedTypeCache(placeType)
}