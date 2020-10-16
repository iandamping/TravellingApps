package com.junemon.core.di.module

import android.content.Context
import com.junemon.core.R
import com.junemon.core.presentation.PresentationConstant.FILENAME
import com.junemon.core.presentation.PresentationConstant.PHOTO_EXTENSION
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
val cameraXModule = module {
    single(named("cameraX")) { provideDirectoryFile(context = androidContext()) }
}

private fun provideDirectoryFile(
    context: Context,
): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    val cameraXDir = if (mediaDir != null && mediaDir.exists())
        mediaDir else context.applicationContext.filesDir

    return File(
        cameraXDir, "$FILENAME$PHOTO_EXTENSION"
    )
}
