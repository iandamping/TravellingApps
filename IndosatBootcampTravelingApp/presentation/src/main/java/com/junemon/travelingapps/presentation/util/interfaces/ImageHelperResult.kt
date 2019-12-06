package com.junemon.travelingapps.presentation.util.interfaces

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import java.io.File

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ImageHelperResult {

    fun getBitmapFromAssets(
        ctx: Context,
        fileName: String,
        widthImage: Int,
        heightImage: Int
    ): Bitmap?

    fun openImageFromGallery(fragment: Fragment)

    fun openImageFromCamera(fragment: Fragment)

    fun getBitmapFromGallery(ctx: Context, path: Uri): Bitmap?

    fun saveImage(views: View, bitmap: Bitmap?)

    fun Fragment.saveImage(scope: CoroutineScope, views: View, imageUrl: String?)

    fun saveFirebaseImageToGallery(
        storageReference: StorageReference,
        views: View,
        lastPathSegment: String?
    )

    fun decodeSampledBitmapFromFile(imageFile: File): Bitmap

    fun bitmapToFile(ctx: Context, bitmap: Bitmap?): File

    fun createImageFileFromPhoto(context: Context, uri: (Uri) -> Unit): File
}