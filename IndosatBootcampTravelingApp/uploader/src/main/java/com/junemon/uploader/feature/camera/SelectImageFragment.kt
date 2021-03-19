package com.junemon.uploader.feature.camera

import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.junemon.core.di.module.CameraXFileDirectory
import com.junemon.uploader.base.fragment.BaseFragment
import com.junemon.uploader.databinding.FragmentSelectImageBinding
import com.junemon.uploader.utility.interfaces.LoadImageHelper
import com.junemon.uploader.vm.SharedViewModel
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 08,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SelectImageFragment : BaseFragment() {
    private var _binding: FragmentSelectImageBinding? = null
    private val binding get() = _binding!!

    @Inject
    @CameraXFileDirectory
    lateinit var cameraXDirectory: File

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper
    private val sharedVm: SharedViewModel by activityViewModels()

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

                sharedVm.setPassedUri(savedUri.toString())

                findNavController().navigate(
                    SelectImageFragmentDirections.actionSelectImageFragmentToUploadFragment()
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