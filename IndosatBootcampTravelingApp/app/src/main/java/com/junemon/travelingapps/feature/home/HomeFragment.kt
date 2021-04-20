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
import com.junemon.travelingapps.util.horizontalRecyclerviewInitializer
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.interfaces.ViewHelper
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
    HomeRandomAdapter.HomeRandomAdapterListener,
    HomeReligiousAdapter.HomeReligiousAdapterListener,
    HomeNatureAdapter.HomeNatureAdapterListener,
    HomeCultureAdapter.HomeCultureAdapterListener {

    private val placeVm: PlaceViewModel by viewModels { viewModelFactory }

    private val randomAdapter: HomeRandomAdapter = HomeRandomAdapter(
        this,
        loadingImageHelper
    )
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
    private var pageSize: Int = 0

    override fun viewCreated() {
        postponeEnterTransition()
        with(binding) {
            startAllShimmer()
            initView()
            root.doOnPreDraw { startPostponedEnterTransition() }
        }
    }

    override fun activityCreated() {
        initData()
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
        rvRandom.apply {
            horizontalRecyclerviewInitializer()
            adapter = randomAdapter
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

        loadingImageHelper.run {
            tbImageLogo.loadWithGlide(
                requireContext().resources.getDrawable(R.drawable.samarinda_logo, null)
            )
        }

        lnSeeAllPlaceCultureType.setOnClickListener {
            navigate(
                HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_culture))
            )
        }
        lnSeeAllPlaceNatureType.setOnClickListener {
            navigate(
                HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_nature))
            )
        }
        lnSeeAllPlaceReligiusType.setOnClickListener {
            navigate(
                HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_religi))
            )
        }
        btnSearchMain.setOnClickListener {

            navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
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

            pageSize = if (result.size > 10) {
                10
            } else result.size

            val religiData = result.filter { it.placeType == "Wisata Religi" }
            val natureData = result.filter { it.placeType == "Wisata Alam" }
            val cultureData = result.filter { it.placeType == "Wisata Budaya" }

            natureAdapter.run {
                submitList(natureData)
                // Force a redraw in case the time zone has changed
                this.notifyDataSetChanged()
            }
            religiousAdapter.run {
                submitList(religiData)
                // Force a redraw in case the time zone has changed
                this.notifyDataSetChanged()
            }
            cultureAdapter.run {
                submitList(cultureData)
                // Force a redraw in case the time zone has changed
                this.notifyDataSetChanged()
            }
            randomAdapter.run {
                submitList(result.take(pageSize))
                // Force a redraw in case the time zone has changed
                this.notifyDataSetChanged()
            }
        }
    }

    private fun FragmentHomeBinding.stopAllShimmer() {
        viewHelper.run {
            stopShimmer()
            shimmerSlider.gone()
            shimmerCultureType.gone()
            shimmerNatureType.gone()
            shimmerReligiusType.gone()
            rvRandom.visible()
            rvPlaceCultureType.visible()
            rvPlaceNatureType.visible()
            rvPlaceReligiusType.visible()
        }
    }

    private fun FragmentHomeBinding.startAllShimmer() {
        viewHelper.run {
            startShimmer()
            shimmerSlider.visible()
            shimmerCultureType.visible()
            shimmerNatureType.visible()
            shimmerReligiusType.visible()
        }
    }

    private fun FragmentHomeBinding.stopShimmer() {
        shimmerSlider.stopShimmer()
        shimmerSlider.hideShimmer()
        shimmerCultureType.stopShimmer()
        shimmerCultureType.hideShimmer()
        shimmerNatureType.stopShimmer()
        shimmerNatureType.hideShimmer()
        shimmerReligiusType.stopShimmer()
        shimmerReligiusType.hideShimmer()
    }

    private fun FragmentHomeBinding.startShimmer() {
        shimmerSlider.startShimmer()
        shimmerCultureType.startShimmer()
        shimmerNatureType.startShimmer()
        shimmerReligiusType.startShimmer()
    }

    override fun onReligiousClicked(data: PlaceCachePresentation) {
        setupExitEnterTransition()

        val toDetailFragment =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )

        val extras =
            FragmentNavigatorExtras(cvItemNatureContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    override fun onNatureClicked(data: PlaceCachePresentation) {
        setupExitEnterTransition()

        val toDetailFragment =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )

        val extras =
            FragmentNavigatorExtras(cvItemNatureContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    override fun onCultureClicked(data: PlaceCachePresentation) {
        setupExitEnterTransition()

        val toDetailFragment =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )

        val extras =
            FragmentNavigatorExtras(cvItemNatureContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    override fun onRandomClicked(data: PlaceCachePresentation) {
        setupExitEnterTransition()

        val toDetailFragment =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                gson.toJson(
                    data
                )
            )

        val extras =
            FragmentNavigatorExtras(cvItemNatureContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun injectDagger() {
        // appComponent().getHomeComponent().provideListener(this, this, this, this).inject(this)
    }
}