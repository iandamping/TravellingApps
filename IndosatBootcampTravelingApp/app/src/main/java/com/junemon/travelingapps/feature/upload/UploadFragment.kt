package com.junemon.travelingapps.feature.upload

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentUploadBinding
import com.junemon.travelingapps.presentation.base.BaseFragment
import kotlin.properties.Delegates

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadFragment : BaseFragment() {
    private var isPermisisonGranted by Delegates.notNull<Boolean>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            checkNotNull(activity) {
                "Current activity is null"
            }
            permissionHelper.getAllPermission(activity!!) {
                isPermisisonGranted = it
            }
        } catch (e: IllegalStateException) {
            commonHelper.timberLogE(e.message)
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
            initView()
        }
        return binding.root
    }

    private fun FragmentUploadBinding.initView() {
        this.apply {
            btnUnggahFoto.setOnClickListener {
                openGalleryAndCamera(isPermisisonGranted)
            }
        }
    }

    private fun openGalleryAndCamera(status: Boolean) {
        try {
            require(status) {
                "Permission not Granted"
            }
            checkNotNull(activity) {
                "Current activity is null"
            }
            val options = arrayOf("Buka Galeri", "Gunakan Kamera")
            AlertDialog.Builder(context)
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> imageHelper.openImageFromGallery(activity!!)
                        1 -> imageHelper.openImageFromCamera(activity!!)
                    }
                    dialog.dismiss()
                }
                .show()
        } catch (e: IllegalStateException) {
            commonHelper.timberLogE(e.message)
        } catch (e: IllegalArgumentException) {
            commonHelper.timberLogE(e.message)
        }
    }
}