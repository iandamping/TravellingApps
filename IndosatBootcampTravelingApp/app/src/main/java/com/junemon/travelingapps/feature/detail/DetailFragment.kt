package com.junemon.travelingapps.feature.detail

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.google.gson.Gson
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.PermissionHelper
import com.junemon.core.presentation.util.transition.themeColor
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DetailFragment : BaseFragment() {
    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    @Inject
    lateinit var intentHelper: IntentHelper

    @Inject
    lateinit var imageHelper: ImageHelper

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var permissionHelper: PermissionHelper

    private val REQUEST_READ_WRITE_CODE_PERMISSIONS = 5
    private val REQUIRED_READ_WRITE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun requestsGranted() =
        permissionHelper.requestGranted(REQUIRED_READ_WRITE_PERMISSIONS)

    private val args: DetailFragmentArgs by navArgs()

    private val passedData by lazy {
        gson.fromJson(
            args.detailData,
            PlaceCachePresentation::class.java
        )
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.financialNavHostFragment
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
        super.onCreate(savedInstanceState)
    }

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            detailData = passedData
            initView(passedData)
            coordinatorParent.transitionName = passedData.placePicture
        }
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
    }

    private fun FragmentDetailBinding.initView(data: PlaceCachePresentation) {
        loadImageHelper.run { ivDetailMovieImages.loadWithGlide(data.placePicture) }
        ivShare.setOnClickListener {
            intentHelper.run {
                if (requestsGranted()) {
                    intentShareImageAndText(
                        requireContext(),
                        data.placeName,
                        data.placeDetail,
                        data.placePicture
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
        ivDownload.setOnClickListener {
            imageHelper.run {
                lifecycleScope.launch {
                    if (data.placePicture != null) {
                        saveImage(
                            coordinatorParent,
                            data.placePicture!!
                        )
                    }

                }

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.run {
            onRequestingPermissionsResult(
                REQUEST_READ_WRITE_CODE_PERMISSIONS,
                requestCode,
                grantResults, {
                    intentHelper.run {
                        intentShareImageAndText(
                            requireContext(),
                            passedData.placeName,
                            passedData.placeDetail,
                            passedData.placePicture
                        )
                    }
                }, {
                    permissionDeniedSnackbar(binding.root)
                })
        }
    }
}