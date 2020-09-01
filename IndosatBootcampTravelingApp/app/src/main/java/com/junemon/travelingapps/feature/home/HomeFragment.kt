package com.junemon.travelingapps.feature.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.junemon.core.presentation.PresentationConstant.placeRvCallback
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.core.presentation.di.factory.viewModelProvider
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentHomeBinding
import com.junemon.travelingapps.feature.home.recycleradapters.HomeCultureAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.HomeNatureAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.HomeReligiousAdapter
import com.junemon.travelingapps.feature.home.recycleradapters.horizontalRecyclerviewInitializer
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview_nature_place.*
import kotlinx.android.synthetic.main.item_recyclerview_random.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragment(),
    HomeReligiousAdapter.HomeReligiousAdapterListener,
    HomeNatureAdapter.HomeNatureAdapterListener,
    HomeCultureAdapter.HomeCultureAdapterListener {

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
    private lateinit var natureAdapter: HomeNatureAdapter
    private lateinit var religiousAdapter: HomeReligiousAdapter
    private lateinit var cultureAdapter: HomeCultureAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var pageSize: Int = 0

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        placeVm = viewModelProvider(viewModelFactory)
        natureAdapter = HomeNatureAdapter(this@HomeFragment)
        religiousAdapter = HomeReligiousAdapter(this@HomeFragment)
        cultureAdapter = HomeCultureAdapter(this@HomeFragment)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        binding.run {
            startAllShimmer()
            initView()
        }
    }

    override fun destroyView() {
        _binding = null
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

    private fun initData() {
        placeVm.getRemote().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Results.Success -> {
                    binding.stopAllShimmer()
                    initRecyclerView(result.data)
                }
                is Results.Error -> {
                    binding.stopAllShimmer()
                }
                is Results.Loading -> {
                    if (!result.cache.isNullOrEmpty()) {
                        binding.stopAllShimmer()
                        initRecyclerView(result.cache)
                    }
                }
            }
        })
    }

    private fun initRecyclerView(result: List<PlaceCacheData>?) {
        universalCatching {
            checkNotNull(result)
            check(result.isNotEmpty())

            pageSize = if (result.size > 10) {
                10
            } else result.size

            val religiData =
                result.mapCacheToPresentation().filter { it.placeType == "Wisata Religi" }
            val natureData =
                result.mapCacheToPresentation().filter { it.placeType == "Wisata Alam" }
            val cultureData =
                result.mapCacheToPresentation().filter { it.placeType == "Wisata Budaya" }

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

            recyclerViewHelper.run {

                binding.rvRandom.setUpHorizontalListAdapter(
                    items = result.mapCacheToPresentation().take(pageSize),
                    diffUtil = placeRvCallback,
                    layoutResId = R.layout.item_recyclerview_random,
                    bindHolder = {
                        loadingImageHelper.run { ivItemRandomPlaceImage.loadWithGlide(it?.placePicture) }
                        tvItemRandomPlaceName.text = it?.placeName
                        tvItemRandomPlaceDistrict.text = it?.placeDistrict
                        when {
                            Build.VERSION.SDK_INT < 24 -> {
                                ViewCompat.setTransitionName(
                                    cvItemRandomContainer,
                                    it?.placePicture
                                )
                            }
                            Build.VERSION.SDK_INT > 24 -> {
                                cvItemRandomContainer.transitionName = it?.placePicture
                            }
                        }
                    }, itemClick = {
                        setupExitEnterTransition()

                        val toDetailFragment =
                            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                gson.toJson(
                                    this
                                )
                            )

                        /**transition name must unique !*/
                        val extras =
                            FragmentNavigatorExtras(cvItemNatureContainer to this!!.placePicture!!)
                        navigate(toDetailFragment, extras)
                    }
                )
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
}