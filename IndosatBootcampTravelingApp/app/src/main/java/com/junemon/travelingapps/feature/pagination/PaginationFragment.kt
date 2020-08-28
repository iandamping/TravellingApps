package com.junemon.travelingapps.feature.pagination

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.junemon.core.presentation.PresentationConstant.placePaginationRvCallback
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.core.presentation.di.factory.viewModelProvider
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.PermissionHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.PlaceCacheData
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentPaginationBinding
import com.junemon.travelingapps.feature.detail.DetailFragmentArgs
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_pagination_recyclerview.*
import kotlinx.android.synthetic.main.item_pagination_recyclerview.view.*
import kotlinx.coroutines.launch
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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var placeVm: PlaceViewModel

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var permissionHelper: PermissionHelper

    private var _binding: FragmentPaginationBinding? = null
    private val binding get() = _binding!!

    private val args: PaginationFragmentArgs by navArgs()

    private val paginationType by lazy {
        args.paginationType
    }

    private val REQUEST_READ_WRITE_CODE_PERMISSIONS = 5
    private val REQUIRED_READ_WRITE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun requestsGranted() =
        permissionHelper.requestGranted(REQUIRED_READ_WRITE_PERMISSIONS)

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
        binding.initView(paginationType)
        binding.run {
            when {
                Build.VERSION.SDK_INT < 24 -> {
                    ViewGroupCompat.setTransitionGroup(rvPagination, true)
                }
                Build.VERSION.SDK_INT > 24 -> {
                    rvPagination.isTransitionGroup = true
                }
            }
        }
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
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
                        when {
                            Build.VERSION.SDK_INT < 24 -> {
                                ViewCompat.setTransitionName(cvItemContainer, it.placePicture)
                            }
                            Build.VERSION.SDK_INT > 24 -> {
                                cvItemContainer.transitionName = it.placePicture
                            }
                        }
                        loadingImageHelper.run {
                            ivFirebaseProfileImage.loadWithGlide(it.placePicture)
                            ivPaginationImage.loadWithGlide(it.placePicture)
                        }
                        ivPaginationSaveImage.setOnClickListener { _ ->
                            imageHelper.run {
                                if (requestsGranted()) {
                                    lifecycleScope.launch {
                                        if (it.placePicture != null) {
                                            saveImage(
                                                relativeParent,
                                                it.placePicture!!
                                            )
                                        }
                                    }
                                } else {
                                    permissionHelper.run {
                                        requestingPermission(
                                            REQUIRED_READ_WRITE_PERMISSIONS,
                                            REQUEST_READ_WRITE_CODE_PERMISSIONS
                                        )
                                    }
                                }
                            }
                        }
                        ivPaginationShare.setOnClickListener { _ ->
                            intentHelper.run {
                                if (requestsGranted()) {
                                        intentShareImageAndText(
                                            requireContext(),
                                            it.placeName,
                                            it.placeDetail,
                                            it.placePicture
                                        )
                                } else {
                                    permissionHelper.run {
                                        requestingPermission(
                                            REQUIRED_READ_WRITE_PERMISSIONS,
                                            REQUEST_READ_WRITE_CODE_PERMISSIONS
                                        )
                                    }
                                }
                            }
                        }
                    }, itemClick = {
                        setupExitEnterTransition()
                        val toDetailFragment =
                            PaginationFragmentDirections.actionPaginationFragmentToDetailFragment(
                                gson.toJson(this)
                            )
                        val extras = FragmentNavigatorExtras(cvItemContainer to this.placePicture!!)
                        navigate(toDetailFragment, extras)
                    })
            }
        }
    }
}