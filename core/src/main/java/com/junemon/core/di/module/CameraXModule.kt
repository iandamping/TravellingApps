package com.junemon.core.di.module

import android.app.Application
import android.content.Context
import com.junemon.core.R
import com.junemon.core.presentation.PresentationConstant.FILENAME
import com.junemon.core.presentation.PresentationConstant.PHOTO_EXTENSION
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
    fun provideDirectoryFile(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        val cameraXDir = if (mediaDir != null && mediaDir.exists())
            mediaDir else context.applicationContext.filesDir

        return File(
            cameraXDir, "$FILENAME$PHOTO_EXTENSION"
        )
    }
}