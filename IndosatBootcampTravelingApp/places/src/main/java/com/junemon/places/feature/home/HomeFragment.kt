package com.junemon.places.feature.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.junemon.core.presentation.PresentationConstant.placeRvCallback
import com.junemon.core.presentation.base.BaseFragment
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.Results
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.places.R
import com.junemon.places.databinding.FragmentHomeBinding
import com.junemon.places.di.sharedPlaceComponent
import com.junemon.places.feature.home.slideradapter.HomeSliderAdapter
import com.junemon.places.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragment() {
    @Inject
    lateinit var viewAdapter:HomeSliderAdapter
    @Inject
    lateinit var viewHelper: ViewHelper
    @Inject
    lateinit var loadingImageHelper: LoadImageHelper
    @Inject
    lateinit var recyclerViewHelper: RecyclerHelper
    @Inject
    lateinit var placeVm: PlaceViewModel

    private val gson by lazy { Gson() }
    private lateinit var binders: FragmentHomeBinding
    private var mHandler: Handler = Handler()
    private var pageSize: Int? = 0
    private var currentPage = 0
    override fun onAttach(context: Context) {
        // inject dagger
        sharedPlaceComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binders = this
            initView()
        }
        return binding.root
    }

    private fun FragmentHomeBinding.initView() {
        this.apply {
            loadingImageHelper.run {
                tbImageLogo.loadWithGlide(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.samarinda_logo
                    )!!
                )
            }
            btnCreate.setOnClickListener {
                it.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToUploadFragment())
            }
            lnSeeAllPlaceCultureType.setOnClickListener {
                it.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_culture))
                )
            }
            lnSeeAllPlaceNatureType.setOnClickListener {
                it.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_nature))
                )
            }
            lnSeeAllPlaceReligiusType.setOnClickListener {
                it.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPaginationFragment(getString(R.string.place_religi))
                )
            }
            btnSearchMain.setOnClickListener {
                it.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }
            initData()
        }
    }

    private fun FragmentHomeBinding.initData() {
            placeVm.getCache().observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Results.Success -> {
                        stopAllShimmer()
                        initViewPager(result.data)
                        initRecyclerView(result.data)
                    }
                    is Results.Error -> {
                        stopAllShimmer()
                        initViewPager(result.cache)
                        initRecyclerView(result.cache)
                    }
                    is Results.Loading -> {
                        startAllShimmer()
                    }
                }
            })
    }

    private fun FragmentHomeBinding.initViewPager(result: List<PlaceCacheData>?) {
        apply {
            ilegallStateCatching {
                checkNotNull(result)
                check(result.isNotEmpty())
                pageSize = if (result.size > 10) {
                    10
                } else result.size
                viewAdapter.addData(result.mapCacheToPresentation().shuffled().take(10))
                vpPlaceRandom.adapter = viewAdapter

                indicator.setViewPager(vpPlaceRandom)
            }
        }
    }

    private fun FragmentHomeBinding.initRecyclerView(result: List<PlaceCacheData>?) {
        apply {
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
                        },
                        layoutResId = R.layout.item_recyclerview, itemClick = {
                            this@apply.root.findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                    gson.toJson(
                                        this
                                    )
                                )
                            )
                        }
                    )
                    rvPlaceCultureType.setUpHorizontalListAdapter(
                        items = cultureData, diffUtil = placeRvCallback,
                        bindHolder = {
                            loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it?.placePicture) }
                            tvItemPlaceName.text = it?.placeName
                            tvItemPlaceDistrict.text = it?.placeDistrict
                        },
                        layoutResId = R.layout.item_recyclerview, itemClick = {
                            this@apply.root.findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                    gson.toJson(
                                        this
                                    )
                                )
                            )
                        }
                    )
                    rvPlaceReligiusType.setUpHorizontalListAdapter(
                        items = religiData, diffUtil = placeRvCallback,
                        bindHolder = {
                            loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it?.placePicture) }
                            tvItemPlaceName.text = it?.placeName
                            tvItemPlaceDistrict.text = it?.placeDistrict
                        },
                        layoutResId = R.layout.item_recyclerview, itemClick = {
                            this@apply.root.findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                    gson.toJson(
                                        this
                                    )
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    private fun FragmentHomeBinding.stopAllShimmer() {
        apply {
            viewHelper.run {
                shimmerSlider.stopShimmer()
                shimmerSlider.hideShimmer()
                shimmerSlider.gone()
                vpPlaceRandom.visible()

                shimmerCultureType.stopShimmer()
                shimmerCultureType.hideShimmer()
                shimmerCultureType.gone()
                rvPlaceCultureType.visible()

                shimmerNatureType.stopShimmer()
                shimmerNatureType.hideShimmer()
                shimmerNatureType.gone()
                rvPlaceNatureType.visible()

                shimmerReligiusType.stopShimmer()
                shimmerReligiusType.hideShimmer()
                shimmerReligiusType.gone()
                rvPlaceReligiusType.visible()
            }
        }
    }

    private fun FragmentHomeBinding.startAllShimmer() {
        apply {
            viewHelper.run {
                shimmerSlider.visible()
                shimmerSlider.startShimmer()

                shimmerCultureType.visible()
                shimmerCultureType.startShimmer()

                shimmerNatureType.visible()
                shimmerNatureType.startShimmer()

                shimmerReligiusType.visible()
                shimmerReligiusType.startShimmer()
            }
        }
    }

    private fun slideRunnable(binding: FragmentHomeBinding) = object : Runnable {
        override fun run() {
            if (currentPage == pageSize) {
                currentPage = 0
            }
            binding.vpPlaceRandom.setCurrentItem(currentPage++, true)
            mHandler.postDelayed(this, 4000L)
        }
    }

    override fun onStart() {
        super.onStart()
        if (::binders.isInitialized) mHandler.postDelayed(slideRunnable(binders), 4000L)
    }

    override fun onStop() {
        super.onStop()
        if (::binders.isInitialized) mHandler.removeCallbacks(slideRunnable(binders))
    }
}