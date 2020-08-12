package com.junemon.travelingapps.feature.camera

import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.junemon.core.di.module.CameraXFileDirectory
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.base.BasePlaceFragment
import com.junemon.travelingapps.databinding.FragmentSelectImageBinding
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 08,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SelectImageFragment : BasePlaceFragment() {
    private var _binding: FragmentSelectImageBinding? = null
    private val binding get() = _binding!!

    @Inject
    @CameraXFileDirectory
    lateinit var cameraXDirectory: File

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            val savedUri = Uri.fromFile(cameraXDirectory)

            val imageFile: File = File(cameraXDirectory.absolutePath)
            val bitmap = BitmapFactory.decodeFile(cameraXDirectory.absolutePath)

            loadingImageHelper.run {
                ivImage.loadWithGlide(bitmap)
            }
            ivImageSelect.setOnClickListener {
                findNavController().navigate(
                    SelectImageFragmentDirections.actionSelectImageFragmentToUploadFragment(
                        savedUri.toString()
                    )
                )
            }
            ivImageDelete.setOnClickListener {
                imageFile.delete()
                MediaScannerConnection.scanFile(
                    view.context, arrayOf(cameraXDirectory.absolutePath), null, null
                )
                findNavController().navigateUp()
            }
        }
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
    }
}