package com.junemon.travelingapps.feature.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.ian.app.helper.data.ResultToConsume
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentHomeBinding
import com.junemon.travelingapps.feature.home.slideradapter.HomeSliderAdapter
import com.junemon.travelingapps.presentation.PresentationConstant.placeRvCallback
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.travelingapps.presentation.model.mapCacheToPresentation
import com.junemon.travelingapps.vm.PlaceViewModel
import com.junemon.travellingapps.domain.model.PlaceCacheData
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragment() {
    private val gson = Gson()
    private lateinit var binders: FragmentHomeBinding
    private val placeVm: PlaceViewModel by viewModel()
    private var mHandler: Handler = Handler()
    private var pageSize: Int? = 0
    private var currentPage = 0

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
            btnCreate.setOnClickListener {
                it.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToUploadFragment())
            }
            initData()
        }
    }

    private fun FragmentHomeBinding.initData() {
        apply {
            placeVm.getCache().observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    ResultToConsume.Status.ERROR -> {
                        stopAllShimmer()
                    }
                    ResultToConsume.Status.SUCCESS -> {
                        stopAllShimmer()
                    }
                    ResultToConsume.Status.LOADING -> {
                        startAllShimmer()
                    }

                }
                initViewPager(result)
                initRecyclerView(result)
            })
        }
    }

    private fun FragmentHomeBinding.initViewPager(result: ResultToConsume<List<PlaceCacheData>>) {
        apply {
            ilegallStateCatching {
                checkNotNull(result.data)
                pageSize = if (result.data!!.size > 10) {
                    10
                } else result.data!!.size
                vpPlaceRandom.adapter = HomeSliderAdapter(result.data!!.mapCacheToPresentation().shuffled().take(10))
                indicator.setViewPager(vpPlaceRandom)
            }
        }
    }

    private fun FragmentHomeBinding.initRecyclerView(result: ResultToConsume<List<PlaceCacheData>>) {
        apply {
            universalCatching {
                checkNotNull(result.data)
                val religiData = result.data!!.mapCacheToPresentation().filter { it.placeType == "Wisata Religi" }
                val natureData = result.data!!.mapCacheToPresentation().filter { it.placeType == "Wisata Alam" }
                val cultureData = result.data!!.mapCacheToPresentation().filter { it.placeType == "Wisata Budaya" }

                recyclerViewHelper.run {

                    rvPlaceNatureType.setUpHorizontalListAdapter(
                        items = natureData, diffUtil = placeRvCallback,
                        bindHolder = {
                            loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it?.placePicture) }
                            tvItemPlaceName.text = it?.placeName
                            tvItemPlaceDistrict.text = it?.placeDistrict
                        },
                        layoutResId = R.layout.item_recyclerview,itemClick = {
                            this@apply.root.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(gson.toJson(this)))
                        }
                    )
                    rvPlaceCultureType.setUpHorizontalListAdapter(
                        items = cultureData, diffUtil = placeRvCallback,
                        bindHolder = {
                            loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it?.placePicture) }
                            tvItemPlaceName.text = it?.placeName
                            tvItemPlaceDistrict.text = it?.placeDistrict
                        },
                        layoutResId = R.layout.item_recyclerview,itemClick = {
                            this@apply.root.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(gson.toJson(this)))
                        }
                    )
                    rvPlaceReligiusType.setUpHorizontalListAdapter(
                        items = religiData, diffUtil = placeRvCallback,
                        bindHolder = {
                            loadingImageHelper.run { ivItemPlaceImage.loadWithGlide(it?.placePicture) }
                            tvItemPlaceName.text = it?.placeName
                            tvItemPlaceDistrict.text = it?.placeDistrict
                        },
                        layoutResId = R.layout.item_recyclerview,itemClick = {
                            this@apply.root.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(gson.toJson(this)))
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