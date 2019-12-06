package com.junemon.travelingapps.feature.pagination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.ian.app.helper.data.ResultToConsume
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentPaginationBinding
import com.junemon.travelingapps.presentation.PresentationConstant.placePaginationRvCallback
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.travelingapps.presentation.model.mapCacheToPresentation
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_pagination_recyclerview.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PaginationFragment : BaseFragment() {
    private val placeVm: PlaceViewModel by viewModel()
    private val paginationType by lazy {
        PaginationFragmentArgs.fromBundle(arguments!!).paginationType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPaginationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pagination, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView(paginationType)
        }
        return binding.root
    }

    private fun FragmentPaginationBinding.initView(type: String) {
        apply {
            placeVm.getSelectedTypeCache(type).observe(viewLifecycleOwner, Observer { result ->
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

                recyclerViewHelper.run {
                    recyclerviewCatching {
                        rvPagination.setUpVerticalListAdapter(
                            items = result.data?.mapCacheToPresentation(),
                            diffUtil = placePaginationRvCallback,
                            layoutResId = R.layout.item_pagination_recyclerview,
                            bindHolder = {
                                tvPaginationName.text = it.placeName
                                tvPaginationAddress.text = it.placeAddres
                                tvPaginationDistrict.text = it.placeDistrict
                                loadingImageHelper.run {
                                    ivFirebaseProfileImage.loadWithGlide(it.placePicture)
                                    ivPaginationImage.loadWithGlide(it.placePicture)
                                }
                                ivPaginationSaveImage.setOnClickListener { _ ->
                                    imageHelper.run {
                                        this@PaginationFragment.saveImage(lifecycleScope, relativeParent, it.placePicture)
                                    }
                                }
                                ivPaginationShare.setOnClickListener { _ ->
                                    intentHelper.run {
                                        this@PaginationFragment.intentShareImageAndText(placeVm.viewModelScope, it.placeName, it.placeDetail, it.placePicture)
                                    }
                                }
                            })
                    }
                }
            })
        }
    }

    private fun FragmentPaginationBinding.stopAllShimmer() {
        apply {
            viewHelper.run {
                shimmerPagination.stopShimmer()
                shimmerPagination.hideShimmer()
                shimmerPagination.gone()
                rvPagination.visible()
            }
        }
    }

    private fun FragmentPaginationBinding.startAllShimmer() {
        apply {
            viewHelper.run {
                shimmerPagination.visible()
                shimmerPagination.startShimmer()
            }
        }
    }
}