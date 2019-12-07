package com.junemon.travelingapps.feature.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.ian.app.helper.util.fromJsonHelper
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentSearchBinding
import com.junemon.travelingapps.presentation.PresentationConstant.placePaginationRvCallback
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.travelingapps.presentation.model.PlaceCachePresentation
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import org.koin.core.inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFragment : BaseFragment() {
    private val placeVm: PlaceViewModel by inject()
    private val gson = Gson()
    private lateinit var binders: FragmentSearchBinding
    private val data by lazy {
        gson.fromJsonHelper<List<PlaceCachePresentation>>(
            SearchFragmentArgs.fromBundle(
                arguments!!
            ).searchItem
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binders = this
            initView()
            initData()
        }
        return binding.root
    }

    private fun FragmentSearchBinding.initView() {
        apply {
            searchViews.queryHint = " Cari tempat "
            searchViews.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!searchViews.isIconified) {
                        searchViews.isIconified = true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchPlace(newText)
                    return false
                }
            })
        }
    }

    @SuppressLint("DefaultLocale")
    private fun searchPlace(s: String?) {
        ilegallStateCatching {
            val tempListData: MutableList<PlaceCachePresentation> = mutableListOf()
            checkNotNull(data)
            data.forEach {
                if (s?.toLowerCase()?.let { it1 -> it.placeName?.toLowerCase()?.contains(it1) }!!) {
                    tempListData.add(it)
                }
            }
            placeVm.setSearchItem(tempListData)
            if (::binders.isInitialized) {
                if (tempListData.isEmpty()) {
                    viewHelper.run {
                        binders.lnSearchFailed.visible()
                        binders.rvSearchPlace.gone()
                    }
                } else {
                    viewHelper.run {
                        binders.lnSearchFailed.gone()
                        binders.rvSearchPlace.visible()
                    }
                }
            }

        }
    }

    private fun FragmentSearchBinding.initData(){
        apply {
            placeVm.searchItem.observe(viewLifecycleOwner, Observer {
                recyclerViewHelper.run {
                    rvSearchPlace.setUpVerticalGridAdapter(
                        items = it,
                        diffUtil = placePaginationRvCallback,
                        layoutResId = R.layout.item_recyclerview,
                        gridSize = 2, bindHolder = {
                            loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it.placePicture) }
                            tvItemPlaceName.text = it.placeName
                            tvItemPlaceDistrict.text = it.placeDistrict
                        }
                    )
                }
            })
        }
    }
}