package com.junemon.travelingapps.feature.search

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.gson.Gson
import com.junemon.core.di.factory.viewModelProvider
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.base.BaseFragmentViewBinding
import com.junemon.travelingapps.databinding.FragmentSearchBinding
import com.junemon.travelingapps.di.injector.appComponent
import com.junemon.travelingapps.util.gridRecyclerviewInitializer
import com.junemon.travelingapps.util.interfaces.ViewHelper
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_search_recyclerview.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFragment : BaseFragmentViewBinding<FragmentSearchBinding>(),
    SearchAdapter.SearchAdapterListener {
    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var placeVm: PlaceViewModel

    private var data: List<PlaceCachePresentation> = mutableListOf()

    override fun activityCreated() {
        initData()
        placeVm.getCache().observe(viewLifecycleOwner, { result ->
            data = result.mapCacheToPresentation()
        })
    }

    private fun FragmentSearchBinding.initView() {
        rvSearchPlace.apply {
            gridRecyclerviewInitializer(2)
            adapter = searchAdapter
        }
        when {
            Build.VERSION.SDK_INT < 24 -> {
                ViewGroupCompat.setTransitionGroup(rvSearchPlace, true)
            }
            Build.VERSION.SDK_INT > 24 -> {
                rvSearchPlace.isTransitionGroup = true
            }
        }

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

    private fun initData() {
        placeVm.searchItem.observe(viewLifecycleOwner, {
            searchAdapter.submitList(it)
        })
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun viewCreated() {
        postponeEnterTransition()
        placeVm = viewModelProvider(viewModelFactory)

        with(binding) {
            initView()
            root.doOnPreDraw { startPostponedEnterTransition() }
        }
    }

    override fun onClicked(data: PlaceCachePresentation) {

        setupExitEnterTransition()

        val toDetailFragment =
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )

        /**transition name must unique !*/
        val extras = FragmentNavigatorExtras(cvItemContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    override fun injectDagger() {
        appComponent().getSearchComponent().provideListener(this).inject(this)
    }
}