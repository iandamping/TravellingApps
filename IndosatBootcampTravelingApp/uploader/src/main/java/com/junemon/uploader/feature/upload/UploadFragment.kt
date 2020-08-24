package com.junemon.uploader.feature.upload

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.core.presentation.di.factory.viewModelProvider
import com.junemon.core.presentation.util.interfaces.CommonHelper
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.PermissionHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.PlaceRemoteData
import com.junemon.model.domain.Results
import com.junemon.uploader.R
import com.junemon.uploader.databinding.FragmentUploadBinding
import com.junemon.uploader.vm.SharedViewModel
import com.junemon.uploader.vm.UploadViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadFragment : BaseFragment() {
    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var imageHelper: ImageHelper

    @Inject
    lateinit var commonHelper: CommonHelper

    @Inject
    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_CAMERA_CODE_PERMISSIONS = 10
    private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private val REQUEST_READ_CODE_PERMISSIONS = 3
    private val REQUIRED_READ_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private val REQUEST_SIGN_IN_PERMISSIONS = 15

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var uploadVm: UploadViewModel

    private val sharedVm: SharedViewModel by activityViewModels()

    private var selectedUriForFirebase :Uri? = null
    private var bitmap :Bitmap? = null
    private var placeType by Delegates.notNull<String>()
    private var placeCity by Delegates.notNull<String>()

    private fun requestCameraPermissionsGranted() = permissionHelper.requestCameraPermissionsGranted(REQUIRED_CAMERA_PERMISSIONS)


    private fun requestReadPermissionsGranted() =  permissionHelper.requestReadPermissionsGranted(REQUIRED_READ_PERMISSIONS)


    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        uploadVm = viewModelProvider(viewModelFactory)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
        uploadVm.getUserProfile().observe(viewLifecycleOwner, { userResult ->
            when (userResult) {
                is Results.Success -> {
                    if (userResult.data.getDisplayName() == null) {
                        viewHelper.run {
                            binding.toolbarUpload.gone(true)
                        }
                        fireSignIn()
                    } else {

                        binding.run {
                            viewHelper.run {
                                binding.toolbarUpload.visible(true)
                            }
                            loadingImageHelper.run {
                                ivUserProfile.loadWithGlide(userResult.data.getPhotoUrl())
                            }
                            tvUserName.text = userResult.data.getDisplayName()
                            btnLogout.setOnClickListener {
                                fireSignOut()
                            }
                        }
                    }
                }
                is Results.Error -> {
                    Timber.e("name ${userResult.exception}")
                }
            }
        })

        sharedVm.passedUri.observe(viewLifecycleOwner, {
            if (it != null) {
                val savedUri = Uri.parse(it)

                bitmap = createBitmapFromUri(savedUri)


                binding.ivPickPhoto.setImageBitmap(bitmap)


                selectedUriForFirebase = savedUri

                viewHelper.run {
                    binding.btnUnggahFoto.gone(true)
                    binding.tvInfoUpload.gone(true)
                    binding.ivPickPhoto.visible(true)
                }
                // val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                //     ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, savedUri))
                // } else {
                //     MediaStore.Images.Media.getBitmap(requireContext().contentResolver, savedUri)
                // }
                // loadingImageHelper.run {
                //     if (bitmap != null) {
                //         binding.ivPickPhoto.loadWithGlide(bitmap)
                //     }
                // }
            }
        })
    }

    private fun FragmentUploadBinding.initView() {
        val allTypeCategory: Array<String> =
            requireContext().resources?.getStringArray(R.array.place_type_items)!!
        val allDistrictCategory: Array<String> =
            requireContext().resources?.getStringArray(R.array.place_districts_items)!!
        val arrayTypeSpinnerAdapter: ArrayAdapter<String>? =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, allTypeCategory)
        val arrayDistrictSpinnerAdapter: ArrayAdapter<String>? =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                allDistrictCategory
            )
        arrayTypeSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        arrayDistrictSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        btnUnggahFoto.setOnClickListener {
            openGalleryAndCamera()
        }

        btnUnggah.setOnClickListener {
            uploadItem()
        }
        spPlaceDistrict.adapter = arrayDistrictSpinnerAdapter
        spPlaceType.adapter = arrayTypeSpinnerAdapter
    }

    private fun FragmentUploadBinding.uploadItem() {
        val placeName = etPlaceName.text.toString()
        val placeDetail = etPlaceDetail.text.toString()
        placeCity = spPlaceDistrict.selectedItem.toString()
        placeType = spPlaceType.selectedItem.toString()
        val placeAddress = etPlaceAddress.text.toString()
        when {
            placeName.isBlank() -> commonHelper.run { etPlaceName.requestError(getString(R.string.place_name_checker)) }
            placeDetail.isBlank() -> commonHelper.run { etPlaceDetail.requestError(getString(R.string.place_description_checker)) }
            placeType.isBlank() -> commonHelper.run { context?.myToast(getString(R.string.place_type_checker)) }
            placeCity.isBlank() -> commonHelper.run { context?.myToast(getString(R.string.place_district_checker)) }
            placeAddress.isBlank() -> commonHelper.run { etPlaceAddress.requestError(getString(R.string.place_address_checker)) }
            else -> {
                ilegallStateCatching {
                    setDialogShow(false)
                    uploadVm.uploadFirebaseData(
                        data = PlaceRemoteData(
                            placeType = placeType,
                            placeName = placeName,
                            placeAddres = placeAddress,
                            placeDistrict = placeCity,
                            placeDetail = placeDetail,
                            placePicture = null
                        ), imageUri = selectedUriForFirebase, success = {
                            setDialogShow(it)
                            clearUri()
                        }, failed = { status, _ ->
                            setDialogShow(status)
                            clearUri()
                        })
                }
            }
        }
    }

    private fun clearUri() {
        selectedUriForFirebase = null
        sharedVm.setPassedUri(null)
        bitmap?.recycle()
        binding.run {
            ivPickPhoto.setImageDrawable(null)
            viewHelper.run {
                btnUnggahFoto.visible(true)
                tvInfoUpload.visible(true)
                ivPickPhoto.gone(true)
            }
        }

    }

    private fun openGalleryAndCamera() {
        universalCatching {
            val options = arrayOf("Buka Galeri", "Gunakan Kamera")
            AlertDialog.Builder(requireContext())
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> {
                            if (requestReadPermissionsGranted()) {
                                openImageFromGallery()
                            } else {
                                permissionHelper.run {
                                    requestingPermission(
                                        REQUIRED_READ_PERMISSIONS,
                                        REQUEST_READ_CODE_PERMISSIONS
                                    )
                                }
                            }
                        }
                        1 -> {
                            if (requestCameraPermissionsGranted()) {
                                findNavController().navigate(UploadFragmentDirections.actionUploadFragmentToOpenCameraFragment())
                            } else {
                                permissionHelper.run {
                                    requestingPermission(
                                        REQUIRED_CAMERA_PERMISSIONS,
                                        REQUEST_CAMERA_CODE_PERMISSIONS
                                    )
                                }
                            }
                        }
                    }
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == FragmentActivity.RESULT_OK) {
            if (requestCode == REQUEST_READ_CODE_PERMISSIONS) {
                universalCatching {
                    requireNotNull(data)
                    requireNotNull(data.data)
                    selectedUriForFirebase = data.data!!

                    val bitmap = imageHelper.getBitmapFromGallery(requireContext(), data.data!!)

                    loadingImageHelper.run {
                        if (bitmap != null) {
                            binding.ivPickPhoto.loadWithGlide(bitmap)
                        }
                    }

                    viewHelper.run {
                        binding.btnUnggahFoto.gone()
                        binding.tvInfoUpload.gone()
                        binding.ivPickPhoto.visible()
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
            onRequestingPermissionsResult(REQUEST_CAMERA_CODE_PERMISSIONS,requestCode, grantResults,{
                findNavController().navigate(UploadFragmentDirections.actionUploadFragmentToOpenCameraFragment())
            },{
                Snackbar.make(
                    binding.root,
                    getString(R.string.permission_not_granted),
                    Snackbar.LENGTH_SHORT
                ).show()
            })


            onRequestingPermissionsResult(REQUEST_READ_CODE_PERMISSIONS,requestCode, grantResults,{
                openImageFromGallery()
            },{
                Snackbar.make(
                    binding.root,
                    getString(R.string.permission_not_granted),
                    Snackbar.LENGTH_SHORT
                ).show()
            })
        }
    }

    private fun openImageFromGallery() {
        val intents = Intent(Intent.ACTION_PICK)
        intents.type = "image/*"
        startActivityForResult(intents, REQUEST_READ_CODE_PERMISSIONS)
    }

    private fun fireSignIn() {
        lifecycleScope.launch {
            val signInIntent = uploadVm.initSignIn()
            startActivityForResult(signInIntent, REQUEST_SIGN_IN_PERMISSIONS)
        }
    }

    private fun fireSignOut() {
        lifecycleScope.launch {
            uploadVm.initLogout {
                Timber.e("success logout")
            }
        }
    }


    private fun createBitmapFromUri(uri: Uri?): Bitmap? =
        if (uri != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        requireContext().contentResolver,
                        uri
                    )
                )
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
        } else {
            null
        }
}