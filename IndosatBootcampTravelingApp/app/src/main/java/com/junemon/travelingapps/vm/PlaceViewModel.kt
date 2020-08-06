package com.junemon.travelingapps.vm

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.core.domain.usecase.PlaceUseCase
import javax.inject.Inject

class PlaceViewModel @Inject constructor(private val repository: PlaceUseCase) : ViewModel() {

    private val _searchItem: MutableLiveData<MutableList<PlaceCachePresentation>> =
        MutableLiveData()

    val searchItem: LiveData<MutableList<PlaceCachePresentation>>
        get() = _searchItem

    fun setSearchItem(data: MutableList<PlaceCachePresentation>) {
        this._searchItem.value = data
    }

    fun getCache(): LiveData<Results<List<PlaceCacheData>>> = repository.getCache()

    fun getSelectedTypeCache(placeType: String): LiveData<Results<List<PlaceCacheData>>> =
        repository.getSelectedTypeCache(placeType)

    suspend fun delete() = repository.delete()

    fun uploadFirebaseData(
        data: PlaceRemoteData,
        imageUri: Uri?,
        success: (Boolean) -> Unit,
        failed: (Boolean, Throwable) -> Unit
    ) = repository.uploadFirebaseData(data, imageUri, success, failed)
}