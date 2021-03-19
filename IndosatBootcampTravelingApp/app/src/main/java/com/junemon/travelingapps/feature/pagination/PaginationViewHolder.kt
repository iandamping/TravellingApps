package com.junemon.travelingapps.feature.pagination

import android.Manifest
import android.os.Build
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.junemon.travelingapps.util.interfaces.ImageHelper
import com.junemon.travelingapps.util.interfaces.IntentHelper
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.interfaces.PermissionHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemPaginationRecyclerviewBinding
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Ian Damping on 19,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PaginationViewHolder(
    private val binding: ItemPaginationRecyclerviewBinding,
    private val permissionHelper: PermissionHelper,
    private val loadImageHelper: LoadImageHelper,
    private val imageHelper: ImageHelper,
    private val intentHelper: IntentHelper,
    private val scope: CoroutineScope
) : RecyclerView.ViewHolder(binding.root) {
    private val REQUEST_READ_WRITE_CODE_PERMISSIONS = 5

    private val REQUIRED_READ_WRITE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun requestsGranted() =
        permissionHelper.requestGranted(REQUIRED_READ_WRITE_PERMISSIONS)

    fun bind(data: PlaceCachePresentation) {
        with(binding) {
            tvPaginationName.text = data.placeName
            tvPaginationAddress.text = data.placeAddres
            tvPaginationDistrict.text = data.placeDistrict
            when {
                Build.VERSION.SDK_INT < 24 -> {
                    ViewCompat.setTransitionName(cvItemContainer, data.placePicture)
                }
                Build.VERSION.SDK_INT > 24 -> {
                    cvItemContainer.transitionName = data.placePicture
                }
            }
            with(loadImageHelper) {
                ivFirebaseProfileImage.loadWithGlide(data.placePicture)
                ivPaginationImage.loadWithGlide(data.placePicture)
            }

            // ivPaginationSaveImage.setOnClickListener { _ ->
            //     with(imageHelper) {
            //         if (requestsGranted()) {
            //             scope.launch {
            //                 if (data.placePicture != null) {
            //                     saveImage(
            //                         root,
            //                         data.placePicture!!
            //                     )
            //                 }
            //             }
            //         } else {
            //             with(permissionHelper) {
            //                 with(fragment) {
            //                     requestingPermission(
            //                         REQUIRED_READ_WRITE_PERMISSIONS,
            //                         REQUEST_READ_WRITE_CODE_PERMISSIONS
            //                     )
            //                 }
            //             }
            //         }
            //     }
            // }
            //
            // ivPaginationShare.setOnClickListener { _ ->
            //     if (requestsGranted()) {
            //         scope.launch {
            //             intentHelper.intentShareImageAndText(
            //                 data.placeName,
            //                 data.placeDetail,
            //                 data.placePicture
            //             ){intent ->
            //                 with(fragment){
            //                     startActivity(
            //                         Intent.createChooser(
            //                             intent,
            //                             "Share Image"
            //                         )
            //                     )
            //                 }
            //             }
            //         }
            //
            //     } else {
            //         with(permissionHelper) {
            //             with(fragment) {
            //                 requestingPermission(
            //                     REQUIRED_READ_WRITE_PERMISSIONS,
            //                     REQUEST_READ_WRITE_CODE_PERMISSIONS
            //                 )
            //             }
            //         }
            //     }
            // }
        }
    }
}