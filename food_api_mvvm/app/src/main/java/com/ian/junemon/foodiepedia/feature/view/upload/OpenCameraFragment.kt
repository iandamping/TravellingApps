package com.ian.junemon.foodiepedia.feature.view.upload

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraXFileDirectory
import com.ian.junemon.foodiepedia.databinding.FragmentOpenCameraBinding
import com.ian.junemon.foodiepedia.feature.vm.SharedViewModel
import com.ian.junemon.foodiepedia.util.FoodConstant.ANIMATION_FAST_MILLIS
import com.ian.junemon.foodiepedia.util.FoodConstant.ANIMATION_SLOW_MILLIS
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.observeEvent
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class OpenCameraFragment : BaseFragmentViewBinding<FragmentOpenCameraBinding>() {
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    @Inject
    @CameraXFileDirectory
    lateinit var cameraXDirectory: File

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null

    private val sharedVm: SharedViewModel by activityViewModels()


    override fun viewCreated() {
        with(binding){
            clicks(cameraCaptureButton){
                takePhoto()
            }
            clicks(cameraSwitchButton){
                lensFacing = isCameraFacingBackOrFront()
                bindCameraUseCases()
            }
        }
    }


    override fun activityCreated() {
        bindCameraUseCases()
        observeNavigation()
    }

    private fun bindCameraUseCases() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            preview = Preview.Builder().build()
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(lensFacing).build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector, preview, imageCapture
                )
                preview?.setSurfaceProvider(binding.viewFinder.createSurfaceProvider())
            } catch (exc: Exception) {
                Timber.e("Use case binding failed : $exc")
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        // Setup image capture metadata
        val metadata = ImageCapture.Metadata().apply {

            // Mirror image when using the front camera
            isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(cameraXDirectory)
            .setMetadata(metadata).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${exc.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val action = OpenCameraFragmentDirections
                        .actionOpenCameraFragmentToSelectImageFragment()
                    sharedVm.setNavigate(action)
                }
            })

        // We can only change the foreground Drawable using API level 23+ API
        flashAnimationAfterTakingPicture()
    }

    private fun flashAnimationAfterTakingPicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Display flash animation to indicate that photo was captured
            with(binding.root) {
                postDelayed({
                    foreground = ColorDrawable(Color.WHITE)
                    postDelayed(
                        { foreground = null }, ANIMATION_FAST_MILLIS
                    )
                }, ANIMATION_SLOW_MILLIS)
            }
        }
    }

    private fun isCameraFacingBackOrFront(): Int =
        if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
            CameraSelector.LENS_FACING_BACK
        } else {
            CameraSelector.LENS_FACING_FRONT
        }

    private fun observeNavigation() {
        observeEvent(sharedVm.navigateEvent) {
            navigate(it)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOpenCameraBinding
        get() = FragmentOpenCameraBinding::inflate
}