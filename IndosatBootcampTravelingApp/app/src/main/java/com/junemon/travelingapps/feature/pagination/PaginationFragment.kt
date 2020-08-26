package com.junemon.travelingapps.feature.pagination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.junemon.core.presentation.PresentationConstant.placePaginationRvCallback
import com.junemon.core.presentation.di.factory.viewModelProvider
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.domain.Results
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentPaginationBinding
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_pagination_recyclerview.view.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PaginationFragment : BaseFragment() {
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

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var placeVm: PlaceViewModel

    @Inject
    lateinit var gson: Gson

    private var _binding: FragmentPaginationBinding? = null
    private val binding get() = _binding!!

    private val paginationType by lazy {
        PaginationFragmentArgs.fromBundle(requireArguments()).paginationType
    }

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaginationBinding.inflate(inflater, container, false)
        placeVm = viewModelProvider(viewModelFactory)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            initView(paginationType)
        }
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
    }

    private fun FragmentPaginationBinding.initView(type: String) {
        placeVm.getSelectedTypeCache(type).observe(viewLifecycleOwner, { result ->
            inflateRecyclerView(result)
        })
    }

    private fun FragmentPaginationBinding.inflateRecyclerView(data: List<PlaceCacheData>?) {
        recyclerViewHelper.run {
            recyclerviewCatching {
                checkNotNull(data)
                check(data.isNotEmpty())
                rvPagination.setUpVerticalListAdapter(
                    items = data.mapCacheToPresentation(),
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
                                lifecycleScope.launch {
                                    this@PaginationFragment.intentShareImageAndText(
                                        it.placeName,
                                        it.placeDetail,
                                        it.placePicture
                                    )
                                }

                            }
                        }
                    }, itemClick = {
                        findNavController().navigate(
                            PaginationFragmentDirections.actionPaginationFragmentToDetailFragment(
                                gson.toJson(this)
                            )
                        )
                    })
            }
        }
    }

}