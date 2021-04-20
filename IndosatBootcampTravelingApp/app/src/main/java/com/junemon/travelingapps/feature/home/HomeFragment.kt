package com.junemon.travelingapps.feature.home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.gson.Gson
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.base.BaseFragmentViewBinding
import com.junemon.travelingapps.databinding.FragmentHomeBinding
import com.junemon.travelingapps.feature.home.recycleradapters.HomeCultureAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.HomeNatureAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.HomeRandomAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.HomeReligiousAdapter
import com.junemon.travelingapps.util.clicks
import com.junemon.travelingapps.util.horizontalRecyclerviewInitializer
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.interfaces.ViewHelper
import com.junemon.travelingapps.util.observeEvent
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview_nature_place.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment @Inject constructor(
    private val viewHelper: ViewHelper,
    private val loadingImageHelper: LoadImageHelper,
    private val gson: Gson,
    private val viewModelFactory: ViewModelProvider.Factory
    ) : BaseFragmentViewBinding<FragmentHomeBinding>(),
    HomeReligiousAdapter.HomeReligiousAdapterListener,
    HomeNatureAdapter.HomeNatureAdapterListener,
    HomeCultureAdapter.HomeCultureAdapterListener {

    private val placeVm: PlaceViewModel by viewModels { viewModelFactory }

    private val natureAdapter: HomeNatureAdapter = HomeNatureAdapter(
        this,
        loadingImageHelper
    )
    private val religiousAdapter: HomeReligiousAdapter = HomeReligiousAdapter(
        this,
        loadingImageHelper
    )
    private val cultureAdapter: HomeCultureAdapter = HomeCultureAdapter(
        this,
        loadingImageHelper
    )

    override fun viewCreated() {
        with(binding) {
            startAllShimmer()
            initView()
        }
    }

    override fun activityCreated() {
        initData()
        obvserveNavigation()
    }

    private fun obvserveNavigation() {
        observeEvent(placeVm.navigateEvent) {
            navigate(it)
        }
    }

    private fun FragmentHomeBinding.initView() {
        rvPlaceCultureType.apply {
            horizontalRecyclerviewInitializer()
            adapter = cultureAdapter
        }
        rvPlaceNatureType.apply {
            horizontalRecyclerviewInitializer()
            adapter = natureAdapter
        }
        rvPlaceReligiusType.apply {
            horizontalRecyclerviewInitializer()
            adapter = religiousAdapter
        }
        when {
            Build.VERSION.SDK_INT < 24 -> {
                ViewGroupCompat.setTransitionGroup(rvPlaceCultureType, true)
                ViewGroupCompat.setTransitionGroup(rvPlaceNatureType, true)
                ViewGroupCompat.setTransitionGroup(rvPlaceReligiusType, true)
            }
            Build.VERSION.SDK_INT > 24 -> {
                rvPlaceCultureType.isTransitionGroup = true
                rvPlaceNatureType.isTransitionGroup = true
                rvPlaceReligiusType.isTransitionGroup = true
            }
        }

        with(loadingImageHelper) {
            tbImageLogo.loadWithGlide(
                requireContext().resources.getDrawable(R.drawable.samarinda_logo, null)
            )
        }

        clicks(lnSeeAllPlaceCultureType) {
            placeVm.setNavigate(HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_culture)))
        }
        clicks(lnSeeAllPlaceNatureType) {
            placeVm.setNavigate(HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_nature)))
        }
        clicks(lnSeeAllPlaceReligiusType) {
            placeVm.setNavigate(HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_religi)))
        }
        clicks(btnSearchMain) {
           placeVm.setNavigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
    }

    private fun initData() {
        placeVm.getRemote().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Results.Success -> {
                    binding.stopAllShimmer()
                    initRecyclerView(result.data.mapCacheToPresentation())
                }
                is Results.Error -> {
                    binding.stopAllShimmer()
                }
                is Results.Loading -> {
                    if (!result.cache.isNullOrEmpty()) {
                        binding.stopAllShimmer()
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

            val religiData = result.filter { it.placeType == "Wisata Religi" }
            val natureData = result.filter { it.placeType == "Wisata Alam" }
            val cultureData = result.filter { it.placeType == "Wisata Budaya" }

            with(natureAdapter) {
                submitList(natureData)
            }
            with(religiousAdapter) {
                submitList(religiData)
            }
            with(cultureAdapter) {
                submitList(cultureData)
            }
        }
    }

    private fun FragmentHomeBinding.stopAllShimmer() {
        with(viewHelper) {
            stopShimmer()
            shimmerCultureType.gone()
            shimmerNatureType.gone()
            shimmerReligiusType.gone()
            rvPlaceCultureType.visible()
            rvPlaceNatureType.visible()
            rvPlaceReligiusType.visible()
        }
    }

    private fun FragmentHomeBinding.startAllShimmer() {
        with(viewHelper) {
            startShimmer()
            shimmerCultureType.visible()
            shimmerNatureType.visible()
            shimmerReligiusType.visible()
        }
    }

    private fun FragmentHomeBinding.stopShimmer() {
        shimmerCultureType.stopShimmer()
        shimmerCultureType.hideShimmer()
        shimmerNatureType.stopShimmer()
        shimmerNatureType.hideShimmer()
        shimmerReligiusType.stopShimmer()
        shimmerReligiusType.hideShimmer()
    }

    private fun FragmentHomeBinding.startShimmer() {
        shimmerCultureType.startShimmer()
        shimmerNatureType.startShimmer()
        shimmerReligiusType.startShimmer()
    }

    override fun onReligiousClicked(data: PlaceCachePresentation) {

        val toDetailFragment =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )
        placeVm.setNavigate(toDetailFragment)
    }

    override fun onNatureClicked(data: PlaceCachePresentation) {
        val toDetailFragment =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )
        placeVm.setNavigate(toDetailFragment)
    }

    override fun onCultureClicked(data: PlaceCachePresentation) {
        val toDetailFragment =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )

        placeVm.setNavigate(toDetailFragment)
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun injectDagger() {
    }
}