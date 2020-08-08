package com.junemon.travelingapps.feature.camera

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
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
import androidx.navigation.fragment.findNavController
import com.junemon.core.di.module.CameraXFileDirectory
import com.junemon.core.presentation.PresentationConstant.ANIMATION_FAST_MILLIS
import com.junemon.core.presentation.PresentationConstant.ANIMATION_SLOW_MILLIS
import com.junemon.travelingapps.base.BasePlaceFragment
import com.junemon.travelingapps.databinding.FragmentOpenCameraBinding
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class OpenCameraFragment : BasePlaceFragment() {
    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    @Inject
    @CameraXFileDirectory
    lateinit var cameraXDirectory: File

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null

    private var _binding: FragmentOpenCameraBinding? = null
    private val binding get() = _binding!!

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cameraCaptureButton.setOnClickListener {
            takePhoto()
        }
        binding.cameraSwitchButton.setOnClickListener {
            lensFacing = isCameraFacingBackOrFront()
            bindCameraUseCases()
        }
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
        if (allPermissionsGranted()) {
            bindCameraUseCases()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
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

        val outputOptions = ImageCapture.OutputFileOptions.Builder(cameraXDirectory).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${exc.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(cameraXDirectory)
                    findNavController().navigate(
                        OpenCameraFragmentDirections
                            .actionOpenCameraFragmentToUploadFragment(savedUri.toString())
                    )
                }
            })

        // We can only change the foreground Drawable using API level 23+ API
        flashAnimationAfterTakingPicture()
    }

    private fun flashAnimationAfterTakingPicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Display flash animation to indicate that photo was captured
            binding.root.run {
                postDelayed({
                    foreground = ColorDrawable(Color.WHITE)
                    postDelayed({ foreground = null }, ANIMATION_FAST_MILLIS
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

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
}