package com.ian.junemon.foodiepedia.feature.view.upload

import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraXFileDirectory
import com.ian.junemon.foodiepedia.databinding.FragmentSelectImageBinding
import com.ian.junemon.foodiepedia.feature.vm.SharedViewModel
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.observeEvent
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 08,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SelectImageFragment : BaseFragmentViewBinding<FragmentSelectImageBinding>() {

    @Inject
    @CameraXFileDirectory
    lateinit var cameraXDirectory: File

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper

    private val sharedVm: SharedViewModel by activityViewModels()

    override fun viewCreated() {
        with(binding) {
            val savedUri = Uri.fromFile(cameraXDirectory)

            val imageFile: File = File(cameraXDirectory.absolutePath)
            val bitmap = BitmapFactory.decodeFile(cameraXDirectory.absolutePath)

            with(loadingImageHelper){
                ivImage.loadWithGlide(bitmap)
            }
            clicks(ivImageSelect) {
                sharedVm.setPassedUri(savedUri.toString())

                val action =SelectImageFragmentDirections.actionSelectImageFragmentToUploadFoodFragment()
                sharedVm.setNavigate(action)
            }
            clicks(ivImageDelete) {
                imageFile.delete()
                MediaScannerConnection.scanFile(
                    requireContext(), arrayOf(cameraXDirectory.absolutePath), null, null
                )
                navigateUp()
            }
        }
    }

    override fun activityCreated() {
        observeNavigation()
    }

    private fun observeNavigation() {
        observeEvent(sharedVm.navigateEvent) {
            navigate(it)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectImageBinding
        get() = FragmentSelectImageBinding::inflate
}