package com.junemon.travelingapps.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.junemon.core.presentation.PresentationConstant.placeRvCallback
import com.junemon.core.presentation.di.factory.viewModelProvider
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.Results
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.R
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.travelingapps.databinding.FragmentHomeBinding
import com.junemon.travelingapps.feature.home.slideradapter.HomeSliderAdapter
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview.*
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragment() {
    @Inject
    lateinit var viewAdapter: HomeSliderAdapter

    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper

    @Inject
    lateinit var recyclerViewHelper: RecyclerHelper

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var placeVm: PlaceViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var pageSize: Int = 0
    private var currentPage = 0

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        placeVm = viewModelProvider(viewModelFactory)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        placeVm.startRunningViewPager()
        binding.run {
            initData()
            initView()
        }
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun destroyView() {
        placeVm.stopRunningViewPager()
        _binding = null
    }

    override fun activityCreated() {
        placeVm.setRunningForever.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                while (it) {
                    if (currentPage == pageSize) {
                        currentPage = 0
                    }
                    delay(4000L)
                    if (_binding != null) {
                        binding.vpPlaceRandom.setCurrentItem(currentPage++, true)
                    }
                }
            }
        })


    }

    private fun FragmentHomeBinding.initView() {
        loadingImageHelper.run {
            tbImageLogo.loadWithGlide(
                requireContext().resources.getDrawable(R.drawable.samarinda_logo, null)
            )
        }

        lnSeeAllPlaceCultureType.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_culture))
            )
        }
        lnSeeAllPlaceNatureType.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_nature))
            )
        }
        lnSeeAllPlaceReligiusType.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_religi))
            )
        }
        btnSearchMain.setOnClickListener {
            findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
    }

    private fun FragmentHomeBinding.initData() {
        placeVm.getRemote().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Results.Success -> {
                    stopAllShimmer()
                    initViewPager(result.data)
                    initRecyclerView(result.data)
                }
                is Results.Error -> {
                    stopAllShimmer()
                }
                is Results.Loading -> {
                    startAllShimmer()
                    if (result.cache!=null){
                        stopAllShimmer()
                        initViewPager(result.cache)
                        initRecyclerView(result.cache)
                    }
                }
            }
        })
    }

    private fun FragmentHomeBinding.initViewPager(result: List<PlaceCacheData>?) {
        ilegallStateCatching {
            checkNotNull(result)
            check(result.isNotEmpty())

            pageSize = if (result.size > 10) {
                10
            } else result.size

            viewAdapter.addData(result.mapCacheToPresentation().take(10))
            vpPlaceRandom.adapter = viewAdapter
            indicator.setViewPager(vpPlaceRandom)

        }
    }

    private fun FragmentHomeBinding.initRecyclerView(result: List<PlaceCacheData>?) {
        universalCatching {
            checkNotNull(result)
            check(result.isNotEmpty())
            val religiData = result.mapCacheToPresentation()
                .filter { it.placeType == "Wisata Religi" }
            val natureData =
                result.mapCacheToPresentation().filter { it.placeType == "Wisata Alam" }
            val cultureData = result.mapCacheToPresentation()
                .filter { it.placeType == "Wisata Budaya" }

            recyclerViewHelper.run {

                rvPlaceNatureType.setUpHorizontalListAdapter(
                    items = natureData, diffUtil = placeRvCallback,
                    bindHolder = {
                        loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it?.placePicture) }
                        tvItemPlaceName.text = it?.placeName
                        tvItemPlaceDistrict.text = it?.placeDistrict
                        cvItemContainer.transitionName = it?.placePicture
                    },
                    layoutResId = R.layout.item_recyclerview, itemClick = {

                        setupExitEnterTransition()

                        val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                            gson.toJson(
                                this
                            )
                        )
                        /**transition name must unique !*/
                        val extras = FragmentNavigatorExtras(cvItemContainer to this!!.placePicture!!)
                        navigateDetail(toDetailFragment, extras)
                    }
                )
                rvPlaceCultureType.setUpHorizontalListAdapter(
                    items = cultureData, diffUtil = placeRvCallback,
                    bindHolder = {
                        loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it?.placePicture) }
                        tvItemPlaceName.text = it?.placeName
                        tvItemPlaceDistrict.text = it?.placeDistrict
                        cvItemContainer.transitionName = it?.placePicture
                    },
                    layoutResId = R.layout.item_recyclerview, itemClick = {
                        setupExitEnterTransition()

                        val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                            gson.toJson(
                                this
                            )
                        )
                        val extras = FragmentNavigatorExtras(cvItemContainer to this!!.placePicture!!)
                        navigateDetail(toDetailFragment, extras)
                    }
                )
                rvPlaceReligiusType.setUpHorizontalListAdapter(
                    items = religiData, diffUtil = placeRvCallback,
                    bindHolder = {
                        loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it?.placePicture) }
                        tvItemPlaceName.text = it?.placeName
                        tvItemPlaceDistrict.text = it?.placeDistrict
                        cvItemContainer.transitionName = it?.placePicture
                    },
                    layoutResId = R.layout.item_recyclerview, itemClick = {
                        setupExitEnterTransition()

                        val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                            gson.toJson(
                                this
                            )
                        )
                        val extras = FragmentNavigatorExtras(cvItemContainer to this!!.placePicture!!)
                        navigateDetail(toDetailFragment, extras)
                    }
                )
            }
        }
    }

    private fun setupExitEnterTransition() {
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }
    }

    private fun FragmentHomeBinding.stopAllShimmer() {
        viewHelper.run {
            stopShimmer()
            shimmerSlider.gone()
            shimmerCultureType.gone()
            shimmerNatureType.gone()
            shimmerReligiusType.gone()
            vpPlaceRandom.visible()
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

    private fun navigateDetail(destination: NavDirections, extraInfo: FragmentNavigator.Extras) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)?.let { navigate(destination, extraInfo) }
    }


}