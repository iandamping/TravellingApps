package com.junemon.core.di.module

import android.app.Application
import com.junemon.core.R
import dagger.Module
import dagger.Provides
import java.io.File

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object CameraXModule {

    @Provides
    @CameraXFileDirectory
    fun provideDirectoryFile(context: Application): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        val cameraXDir = if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir

        return File(
            cameraXDir, "Places.jpg"
        )
    }
}