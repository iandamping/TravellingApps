package com.junemon.travelingapps.feature.upload

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentUploadBinding
import com.junemon.travelingapps.presentation.PresentationConstant.RequestSelectGalleryImage
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.travelingapps.vm.PlaceViewModel
import com.junemon.travellingapps.domain.model.PlaceRemoteData
import kotlinx.android.synthetic.main.fragment_upload.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadFragment : BaseFragment() {
    private var isPermisisonGranted by Delegates.notNull<Boolean>()
    private var selectedUriForFirebase by Delegates.notNull<Uri>()
    private var placeType by Delegates.notNull<String>()
    private var placeCity by Delegates.notNull<String>()
    private val placeVm: PlaceViewModel by viewModel()
    private lateinit var binders: FragmentUploadBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBaseDialog()
        ilegallArgumenCatching {
            checkNotNull(activity)
            permissionHelper.getAllPermission(activity!!) {
                isPermisisonGranted = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentUploadBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binders = this
            initView()
        }
        return binding.root
    }

    private fun FragmentUploadBinding.initView() {
        this.apply {
            val allTypeCategory:Array<String> = context?.resources?.getStringArray(R.array.place_type_items)!!
            val allDistrictCategory:Array<String> = context?.resources?.getStringArray(R.array.place_districts_items)!!
            val arrayTypeSpinnerAdapter: ArrayAdapter<String>? =  ArrayAdapter(context!!, android.R.layout.simple_spinner_item, allTypeCategory)
            val arrayDistrictSpinnerAdapter: ArrayAdapter<String>? =  ArrayAdapter(context!!, android.R.layout.simple_spinner_item, allDistrictCategory)
            arrayTypeSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            arrayDistrictSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            btnUnggahFoto.setOnClickListener {
                openGalleryAndCamera(isPermisisonGranted)
            }
            btnUnggah.setOnClickListener {
                uploadItem()
            }
            spPlaceDistrict.adapter = arrayDistrictSpinnerAdapter
            spPlaceType.adapter = arrayTypeSpinnerAdapter
        }
    }

    private fun FragmentUploadBinding.uploadItem() {
        apply {
            val placeName = etPlaceName.text.toString()
            val placeDetail = etPlaceDetail.text.toString()
            placeCity= spPlaceDistrict.selectedItem.toString()
            placeType = spPlaceType.selectedItem.toString()
            val placeAddress = etPlaceAddress.text.toString()
            when {
                placeName.isBlank() -> viewHelper.run { etPlaceName.requestError(getString(R.string.place_name_checker)) }
                placeDetail.isBlank() -> viewHelper.run { etPlaceDetail.requestError(getString(R.string.place_description_checker)) }
                placeType.isBlank() -> commonHelper.run { context?.myToast(getString(R.string.place_type_checker)) }
                placeCity.isBlank() -> commonHelper.run { context?.myToast(getString(R.string.place_district_checker)) }
                placeAddress.isBlank() -> viewHelper.run { etPlaceAddress.requestError(getString(R.string.place_address_checker)) }
                else -> {
                    ilegallStateCatching {
                        checkNotNull(selectedUriForFirebase)
                        setDialogShow(false)
                        placeVm.uploadFirebaseData(
                            data = PlaceRemoteData(
                                placeType = placeType,
                                placeName = placeName,
                                placeAddres = placeAddress,
                                placeDistrict = placeCity,
                                placeDetail = placeDetail,
                                placePicture = null
                            ), imageUri = selectedUriForFirebase, success = {
                                setDialogShow(it)
                                moveUp()
                            }, failed = { status, throwable ->
                                setDialogShow(status)
                                commonHelper.timberLogE(throwable.message)
                                moveUp()
                            })
                    }
                }
            }
        }
    }

    private fun FragmentUploadBinding.moveUp(){
        this.root.findNavController().navigateUp()
    }

    private fun openGalleryAndCamera(status: Boolean) {
        universalCatching {
            require(status)
            val options = arrayOf("Buka Galeri", "Gunakan Kamera")
            AlertDialog.Builder(context)
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> imageHelper.openImageFromGallery(this)
                        1 -> imageHelper.openImageFromCamera(this)
                    }
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == FragmentActivity.RESULT_OK) {
            if (requestCode == RequestSelectGalleryImage) {
                universalCatching {
                    requireNotNull(data)
                    requireNotNull(data.data)
                    checkNotNull(context)
                    selectedUriForFirebase = data.data!!
                    val bitmap = imageHelper.getBitmapFromGallery(context!!, data.data!!)
                    if (::binders.isInitialized) {
                        viewHelper.run {
                            btnUnggahFoto.gone()
                            tvInfoUpload.gone()
                            ivPickPhoto.visible()
                        }
                        binders.ivPickPhoto.setImageBitmap(bitmap)
                    }
                }
            } else {
                ilegallStateCatching {
                    checkNotNull(context)
                    val bitmap = imageHelper.decodeSampledBitmapFromFile(
                        imageHelper.createImageFileFromPhoto(context!!) {
                            selectedUriForFirebase = it
                        }
                    )
                    if (::binders.isInitialized) {
                        viewHelper.run {
                            btnUnggahFoto.gone()
                            tvInfoUpload.gone()
                            ivPickPhoto.visible()
                        }
                        binders.ivPickPhoto.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}