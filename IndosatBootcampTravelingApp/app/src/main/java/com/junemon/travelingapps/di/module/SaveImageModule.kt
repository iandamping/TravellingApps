package com.junemon.travelingapps.di.module

import android.content.Context
import com.junemon.travelingapps.R
import com.junemon.travelingapps.di.qualifier.DefaultCameraFileDirectory
import dagger.Module
import dagger.Provides
import java.io.File

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object SaveImageModule {

    private const val FILENAME = "Places"
    private const val PHOTO_EXTENSION = ".jpg"
    private val PHOTO_FILE = FILENAME + System.currentTimeMillis() + PHOTO_EXTENSION

    @Provides
    @DefaultCameraFileDirectory
    fun provideCameraXDirectoryFile(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        val cameraXDir = if (mediaDir != null && mediaDir.exists())
            mediaDir else context.applicationContext.filesDir

        return File(
            cameraXDir, PHOTO_FILE
        )
    }
}