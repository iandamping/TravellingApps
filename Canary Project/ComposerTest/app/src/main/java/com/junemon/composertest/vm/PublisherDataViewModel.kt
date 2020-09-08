package com.junemon.gamesapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ian.app.helper.base.BaseViewModel
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.domain2.usecase.PublisherUseCase
import com.junemon.gamesapi.model.PublisherPresentation
import com.junemon.gamesapi.model.mapListToPresentation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ian Damping on 29,November,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PublisherDataViewModel(private val repository:PublisherUseCase): BaseViewModel() {
    private val compositeDisposable by lazy { CompositeDisposable() }
    private val _publisherData: MutableLiveData<ResultToConsume<List<PublisherPresentation>>> =
        MutableLiveData()
    val publisherData: LiveData<ResultToConsume<List<PublisherPresentation>>>
        get() = _publisherData

    fun get(){
        compositeDisposable.add(repository.get().doOnSubscribe {
            _publisherData.postValue(ResultToConsume.loading())
        }.subscribeOn(Schedulers.io()).map {
            it.mapListToPresentation()
        }.subscribe({
            _publisherData.postValue(ResultToConsume.success(it))
        }, {
            _publisherData.postValue(ResultToConsume.error(it.message!!))
        }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}