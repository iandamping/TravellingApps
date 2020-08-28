package com.junemon.core.presentation.util.classes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.ShareCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.RequestManager
import com.junemon.core.R
import com.junemon.core.data.di.IoDispatcher
import com.junemon.core.data.di.MainDispatcher
import com.junemon.core.presentation.PresentationConstant
import com.junemon.core.presentation.util.interfaces.IntentHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jetbrains.anko.indeterminateProgressDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class IntentUtilImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val context: Context
) : IntentHelper {

    private fun provideSavedDirectoryFile(): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        val designationDir = if (mediaDir != null && mediaDir.exists())
            mediaDir else context.applicationContext.filesDir

        return File(
            designationDir,
            "${PresentationConstant.FILENAME}${System.currentTimeMillis()}${PresentationConstant.PHOTO_EXTENSION}"
        )
    }

    private fun getLocalBitmapUri(bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        val imageFile = provideSavedDirectoryFile()
        try {
            val out = FileOutputStream(imageFile)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(imageFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    override fun intentShareText(activity: FragmentActivity, text: String) {
        val shareIntent = ShareCompat.IntentBuilder.from(activity)
            .setText(text)
            .setType("text/plain")
            .createChooserIntent()
            .apply {
                // https://android-developers.googleblog.com/2012/02/share-with-intents.html
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // If we're on Lollipop, we can open the intent as a document
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                } else {
                    // Else, we will use the old CLEAR_WHEN_TASK_RESET flag
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                }
            }
        activity.startActivity(shareIntent)
    }

    override fun intentShareImageAndText(
        viewControllerContext: Context,
        tittle: String?,
        message: String?,
        imageUrl: String?
    ) {
        val dialogs = viewControllerContext.indeterminateProgressDialog(
            viewControllerContext.resources.getString(R.string.please_wait),
            viewControllerContext.resources.getString(R.string.processing_image)
        )
        try {
            requireNotNull(tittle) {
                "tittle to share is null"
            }
            requireNotNull(message) {
                "message to share is null"
            }
            requireNotNull(imageUrl) {
                "picture to share is null"
            }
            runBlocking {
                withContext(ioDispatcher) {
                    try {
                        dialogs.show()
                        val url = URL(imageUrl)
                        val input = url.openStream()
                        BitmapFactory.decodeStream(input)
                    } catch (e: IOException) {
                        null
                    }
                }?.let { bitmap ->
                    if (getLocalBitmapUri(bitmap) != null) {
                        dialogs.dismiss()
                        val sharingIntent = Intent(Intent.ACTION_SEND)
                        sharingIntent.type = "image/*"
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap))
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, tittle)
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, message)
                        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        viewControllerContext.startActivity(
                            Intent.createChooser(
                                sharingIntent,
                                "Share Image"
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun intentOpenWebsite(activity: Activity, url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        activity.startActivity(openURL)
    }
}

/*
withContext(ioDispatcher) {
    val bitmap = requestManager
        .asBitmap()
        .load(imageUrl)
        .submit(512, 512)
        .get()
    if (getLocalBitmapUri(bitmap) != null) {
        dialogs.dismiss()
        withContext(mainDispatcher) {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "image/*"
            sharingIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap))
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, tittle)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, message)
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            viewControllerContext.startActivity(
                Intent.createChooser(
                    sharingIntent,
                    "Share Image"
                )
            )
        }
    }
}
}*/
 */
