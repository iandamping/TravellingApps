package com.junemon.travelingapps.feature.pagination

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.junemon.model.domain.Results
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.activity.MainActivity
import com.junemon.travelingapps.databinding.FragmentPaginationBinding
import com.junemon.travelingapps.presentation.PresentationConstant.placePaginationRvCallback
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.travelingapps.presentation.util.interfaces.ImageHelper
import com.junemon.travelingapps.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.presentation.util.interfaces.RecyclerHelper
import com.junemon.travelingapps.presentation.util.interfaces.ViewHelper
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_pagination_recyclerview.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PaginationFragment : BaseFragment() {
    private val gson = Gson()
    @Inject
    lateinit var viewHelper: ViewHelper
    @Inject
    lateinit var recyclerViewHelper: RecyclerHelper
    @Inject
    lateinit var loadingImageHelper: LoadImageHelper
    @Inject
    lateinit var imageHelper: ImageHelper
    @Inject
    lateinit var intentHelper: IntentHelper
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val placeVm: PlaceViewModel by viewModels { viewModelFactory }

    private val paginationType by lazy {
        PaginationFragmentArgs.fromBundle(arguments!!).paginationType
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).activityComponent.getFeatureComponent()
            .create().inject(this)
        super.onAttach(context)
        // dont use this, but i had to
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
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
                when (result) {
                    is Results.Error -> {
                        stopAllShimmer()
                    }
                    is Results.Success -> {
                        stopAllShimmer()
                        recyclerViewHelper.run {
                            recyclerviewCatching {
                                requireNotNull(result.data)
                                rvPagination.setUpVerticalListAdapter(
                                    items = result.data.mapCacheToPresentation(),
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
                                                this@PaginationFragment.saveImage(
                                                    lifecycleScope,
                                                    relativeParent,
                                                    it.placePicture
                                                )
                                            }
                                        }
                                        ivPaginationShare.setOnClickListener { _ ->
                                            intentHelper.run {
                                                this@PaginationFragment.intentShareImageAndText(
                                                    placeVm.viewModelScope,
                                                    it.placeName,
                                                    it.placeDetail,
                                                    it.placePicture
                                                )
                                            }
                                        }
                                    }, itemClick = {
                                        this@apply.root.findNavController().navigate(
                                            PaginationFragmentDirections.actionPaginationFragmentToDetailFragment(
                                                gson.toJson(this)
                                            )
                                        )
                                    })
                            }
                        }
                    }
                    is Results.Loading -> {
                        startAllShimmer()
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