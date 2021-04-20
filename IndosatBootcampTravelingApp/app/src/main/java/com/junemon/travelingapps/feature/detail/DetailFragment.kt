package com.junemon.travelingapps.feature.detail

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.google.gson.Gson
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.base.BaseFragmentDataBinding
import com.junemon.travelingapps.databinding.FragmentDetailBinding
import com.junemon.travelingapps.di.injector.appComponent
import com.junemon.travelingapps.util.clicks
import com.junemon.travelingapps.util.interfaces.ImageHelper
import com.junemon.travelingapps.util.interfaces.IntentHelper
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.interfaces.PermissionHelper
import com.junemon.travelingapps.util.transition.themeColor
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DetailFragment @Inject constructor(
    private val loadImageHelper: LoadImageHelper,
    private val intentHelper: IntentHelper,
    private val imageHelper: ImageHelper,
    private val gson: Gson,
    private val permissionHelper: PermissionHelper
) : BaseFragmentDataBinding<FragmentDetailBinding>() {

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

    override fun activityCreated() {
    }

    private fun FragmentDetailBinding.initView(data: PlaceCachePresentation) {
        clicks(ivBack) {
            navigateUp()
        }
        with(loadImageHelper) { ivDetailMovieImages.loadWithGlide(data.placePicture) }
        clicks(ivShare) {
            if (requestsGranted()) {
                consumeSuspend {
                    setDialogShow(false)
                    intentHelper.intentShareImageAndText(
                        data.placeName,
                        data.placeDetail,
                        data.placePicture
                    ) {
                        setDialogShow(true)
                        sharedImageIntent(it)
                    }
                }
            } else {
                with(permissionHelper) {
                    requestingPermission(
                        REQUIRED_READ_WRITE_PERMISSIONS,
                        REQUEST_READ_WRITE_CODE_PERMISSIONS
                    )
                }
            }

        }

        clicks(ivDownload) {
            with(imageHelper) {
                consumeSuspend {
                    if (data.placePicture != null) {
                        saveImage(
                            binding.root,
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
        with(permissionHelper) {
            onRequestingPermissionsResult(
                REQUEST_READ_WRITE_CODE_PERMISSIONS,
                requestCode,
                grantResults, {
                    consumeSuspend {
                        setDialogShow(false)
                        intentHelper.intentShareImageAndText(
                            passedData.placeName,
                            passedData.placeDetail,
                            passedData.placePicture
                        ) {
                            setDialogShow(true)
                            sharedImageIntent(it)
                        }
                    }

                }, {
                    permissionDeniedSnackbar(binding.root)
                })
        }
    }

    private fun permissionDeniedSnackbar(view: View) {
        Snackbar.make(
            view,
            getString(R.string.permission_not_granted),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override fun viewCreated() {
        with(binding) {
            detailData = passedData
            initView(passedData)
        }
    }

    override fun injectDagger() {
    }
}