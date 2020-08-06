package com.junemon.travelingapps.feature.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.junemon.core.presentation.PresentationConstant.placePaginationRvCallback
import com.junemon.core.presentation.di.factory.viewModelProvider
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.base.BasePlaceFragment
import com.junemon.travelingapps.databinding.FragmentSearchBinding
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFragment : BasePlaceFragment() {
    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var recyclerViewHelper: RecyclerHelper

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var placeVm: PlaceViewModel

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var data: List<PlaceCachePresentation> = mutableListOf()

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        placeVm = viewModelProvider(viewModelFactory)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            initData()
            initView()
        }
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
        placeVm.getCache().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Results.Success -> {
                    data = result.data.mapCacheToPresentation()
                }
                is Results.Error -> {
                    val cache by lazy { result.cache?.mapCacheToPresentation() }
                    if (cache != null) {
                        data = cache!!
                    }
                }
            }
        })
    }

    private fun FragmentSearchBinding.initView() {
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

    @SuppressLint("DefaultLocale")
    private fun searchPlace(s: String?) {
        ilegallStateCatching {
            val tempListData: MutableList<PlaceCachePresentation> = mutableListOf()
            check(data.isNotEmpty())
            data.forEach {
                if (s?.toLowerCase()?.let { it1 -> it.placeName?.toLowerCase()?.contains(it1) }!!) {
                    tempListData.add(it)
                }
            }
            placeVm.setSearchItem(tempListData)
            if (tempListData.isEmpty()) {
                viewHelper.run {
                    binding.lnSearchFailed.visible()
                    binding.rvSearchPlace.gone()
                }
            } else {
                viewHelper.run {
                    binding.lnSearchFailed.gone()
                    binding.rvSearchPlace.visible()
                }
            }
        }
    }

    private fun FragmentSearchBinding.initData() {
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
                    }, itemClick = {
                        findNavController().navigate(
                            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                                gson.toJson(
                                    this
                                )
                            )
                        )
                    }
                )
            }
        })
    }
}