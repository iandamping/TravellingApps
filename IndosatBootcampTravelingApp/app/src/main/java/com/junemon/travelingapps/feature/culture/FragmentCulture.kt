package com.junemon.travelingapps.feature.culture

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.gson.Gson
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.core.presentation.di.factory.viewModelProvider
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.databinding.FragmentCultureBinding
import com.junemon.travelingapps.feature.onboard.FragmentOnBoardDirections
import com.junemon.travelingapps.utils.staggeredVerticalRecyclerviewInitializer
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview_culture_place.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 03,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FragmentCulture : BaseFragment(), HomeCultureAdapter.HomeCultureAdapterListener {
    private var _binding: FragmentCultureBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var cultureAdapter: HomeCultureAdapter
    private lateinit var placeVm: PlaceViewModel

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCultureBinding.inflate(inflater, container, false)
        placeVm = viewModelProvider(viewModelFactory)
        cultureAdapter = HomeCultureAdapter(this)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
        initData()
    }

    override fun onCultureClicked(data: PlaceCachePresentation) {
        setupExitEnterTransition()

        val toDetailFragment =
            FragmentOnBoardDirections.actionFragmentOnBoardToDetailFragment(
                gson.toJson(
                    data
                )
            )

        val extras =
            FragmentNavigatorExtras(cvItemCultureContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    private fun FragmentCultureBinding.initView() {
        when {
            Build.VERSION.SDK_INT < 24 -> {
                ViewGroupCompat.setTransitionGroup(rvCulture, true)
            }
            Build.VERSION.SDK_INT > 24 -> {
                rvCulture.isTransitionGroup = true
            }
        }
        rvCulture.apply {
            staggeredVerticalRecyclerviewInitializer()
            adapter = cultureAdapter
        }
    }

    private fun initData() {
        placeVm.getRemote().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Results.Success -> {
                    initRecyclerView(result.data.mapCacheToPresentation())
                }
                is Results.Error -> {
                }
                is Results.Loading -> {
                    if (!result.cache.isNullOrEmpty()) {
                        initRecyclerView(result.cache?.mapCacheToPresentation())
                    }
                }
            }
        })
    }

    private fun initRecyclerView(result: List<PlaceCachePresentation>?) {
        universalCatching {
            checkNotNull(result)
            check(result.isNotEmpty())

            val cultureData = result.filter { it.placeType == "Wisata Budaya" }

            cultureAdapter.run {
                submitList(cultureData)
                // Force a redraw in case the time zone has changed
                this.notifyDataSetChanged()
            }

        }
    }
}