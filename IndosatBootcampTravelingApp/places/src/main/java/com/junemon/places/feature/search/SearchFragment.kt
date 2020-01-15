package com.junemon.travelingapps.feature.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.activity.MainActivity
import com.junemon.travelingapps.databinding.FragmentSearchBinding
import com.junemon.travelingapps.presentation.PresentationConstant.placePaginationRvCallback
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.presentation.util.interfaces.RecyclerHelper
import com.junemon.travelingapps.presentation.util.interfaces.ViewHelper
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFragment : BaseFragment() {
    private val gson = Gson()
    @Inject
    lateinit var viewHelper: ViewHelper
    @Inject
    lateinit var recyclerViewHelper: RecyclerHelper
    @Inject
    lateinit var loadingImageHelper: LoadImageHelper
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val placeVm: PlaceViewModel by viewModels { viewModelFactory }
    private lateinit var binders: FragmentSearchBinding
    private var data: List<PlaceCachePresentation> = mutableListOf()

    override fun onAttach(context: Context) {
        (activity as MainActivity).activityComponent.getFeatureComponent()
            .create().inject(this)
        super.onAttach(context)
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
            initData()
            initView()
        }
        return binding.root
    }

    private fun FragmentSearchBinding.initView() {
        apply {
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
            check(data.isNotEmpty())
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

    private fun FragmentSearchBinding.initData() {
        apply {
            placeVm.getCache().observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Results.Success -> {
                        data = result.data.mapCacheToPresentation()
                    }
                }
            })

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
                            this@apply.root.findNavController().navigate(
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
}