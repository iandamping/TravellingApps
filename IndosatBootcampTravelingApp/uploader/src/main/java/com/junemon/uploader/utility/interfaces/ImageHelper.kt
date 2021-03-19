package com.junemon.uploader.utility.interfaces

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import java.io.File

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ImageHelper {

    fun getBitmapFromAssets(
        fileName: String,
        widthImage: Int,
        heightImage: Int
    ): Bitmap?

    fun openImageFromGallery(fragment: Fragment)

    fun openImageFromCamera(fragment: Fragment)

    fun getBitmapFromGallery(path: Uri): Bitmap?

    fun saveImage(views: View, bitmap: Bitmap?)

    suspend fun saveImage(openingSnackbarFromViews: View, imageUrl: String)

    fun saveFirebaseImageToGallery(
        views: View,
        lastPathSegment: String?
    )

    fun decodeSampledBitmapFromFile(imageFile: File): Bitmap

    fun bitmapToFile( bitmap: Bitmap?): File

    fun createImageFileFromPhoto(uri: (Uri) -> Unit): File
}