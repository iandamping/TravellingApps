package com.junemon.travelingapps.feature.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.base.BaseFragmentViewBinding
import com.junemon.travelingapps.databinding.FragmentSearchBinding
import com.junemon.travelingapps.util.gridRecyclerviewInitializer
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.interfaces.ViewHelper
import com.junemon.travelingapps.util.observeEvent
import com.junemon.travelingapps.vm.PlaceViewModel
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val gson: Gson,
    loadImageHelper: LoadImageHelper,
    private val viewHelper: ViewHelper
) : BaseFragmentViewBinding<FragmentSearchBinding>(),
    SearchAdapter.SearchAdapterListener {

    private val searchAdapter: SearchAdapter = SearchAdapter(this, loadImageHelper)

    private val placeVm: PlaceViewModel by viewModels { viewModelFactory }

    override fun activityCreated() {
        initData()
        obvserveNavigation()
    }

    private fun obvserveNavigation() {
        observeEvent(placeVm.navigateEvent) {
            navigate(it)
        }
    }

    private fun FragmentSearchBinding.initView() {
        rvSearchPlace.apply {
            gridRecyclerviewInitializer(2)
            adapter = searchAdapter
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
                if (!newText.isNullOrEmpty()) {
                    searchPlace(newText)
                }
                return false
            }
        })
    }

    @SuppressLint("DefaultLocale")
    private fun searchPlace(s: String) {
        placeVm.getCache().observe(viewLifecycleOwner, { result ->
            placeVm.setSearchItem(result.mapCacheToPresentation().filter {
                it.placeName?.toLowerCase()?.contains(s)!!
            })

        })
    }

    private fun initData() {
        placeVm.getCache().observe(viewLifecycleOwner){
            searchAdapter.submitList(it.mapCacheToPresentation())
        }
        placeVm.searchItem.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                with(viewHelper) {
                    binding.lnSearchFailed.visible()
                    binding.rvSearchPlace.gone()
                }
            } else {
                with(searchAdapter){
                    submitList(it)
                    notifyDataSetChanged()
                }
                with(viewHelper) {
                    binding.lnSearchFailed.gone()
                    binding.rvSearchPlace.visible()
                }
            }
        })
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun viewCreated() {
        with(binding) {
            initView()
            root.doOnPreDraw { startPostponedEnterTransition() }
        }
    }

    override fun onClicked(data: PlaceCachePresentation) {
        val toDetailFragment =
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )
        placeVm.setNavigate(toDetailFragment)
    }

    override fun injectDagger() {
    }
}