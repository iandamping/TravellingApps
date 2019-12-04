package com.junemon.travelingapps.presentation.util.classes

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.StorageReference
import com.ian.app.helper.interfaces.CommonHelperResult
import com.junemon.travelingapps.presentation.R
import com.junemon.travelingapps.presentation.util.PresentationConstant.saveFilterImagePath
import com.junemon.travelingapps.presentation.util.interfaces.ImageUtilHelper
import org.jetbrains.anko.progressDialog
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
internal class ImageUtil : ImageUtilHelper, KoinComponent {

    private val helper: CommonHelperResult by inject()
    private val maxWidth = 612
    private val maxHeight = 816

    override fun getBitmapFromAssets(
        ctx: Context,
        fileName: String,
        widthImage: Int,
        heightImage: Int
    ): Bitmap? {
        val assetManager: AssetManager = ctx.assets!!
        val inputStreams: InputStream
        try {
            val options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            inputStreams = assetManager.open(fileName)
            //calculate sample size
            options.inSampleSize = calculateSampleSize(options, widthImage, heightImage)
            //decode bitmpat with samplesize set
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(inputStreams, null, options)
        } catch (e: IOException) {
            helper.timberLogE(e.message)
        }
        return null
    }

    override fun getBitmapFromGallery(ctx: Context, path: Uri): Bitmap? {
        val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            ctx.applicationContext?.contentResolver?.query(path, filePathColum, null, null, null)
        cursor?.moveToFirst()

        val columnIndex: Int? = cursor?.getColumnIndex(filePathColum[0])
        val picturePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()

        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(picturePath, options)

        options.inSampleSize = calculateSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(picturePath, options)
    }

    override fun saveImage(views: View, bitmap: Bitmap?) {
        try {
            requireNotNull(bitmap){
                "Bitmap that needs to save is null"
            }
            val pictureDirectory = Environment.getExternalStorageDirectory()
            val imageFile = File(pictureDirectory, saveFilterImagePath)
            val quality = 100
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            openImageFromSnackbar(views, imageFile)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        voidCustomMediaScannerConnection(views.context, saveFilterImagePath)
    }

    override fun saveFirebaseImageToGallery(
        storageReference: StorageReference,
        views: View,
        lastPathSegment: String?
    ) {
        try {
            requireNotNull(lastPathSegment){
                "Image last path is null"
            }
            val dialogs = views.context.progressDialog(
                views.context.resources?.getString(R.string.please_wait),
                views.context.resources?.getString(R.string.downloading_image)
            )
            val spaceRef = storageReference.child(lastPathSegment)
            val pictureDirectory = Environment.getExternalStorageDirectory()
            val imageFile = File(pictureDirectory, saveFilterImagePath)
            /*
            kita memerlukan variable file agar menampung image dari firebase storage kita
            perhatikan juga pada bagian storage refence nya kita memerlukan nama file / last path segment image kita agar tau
            image mana yg akan di download
             */
            spaceRef.getFile(imageFile).addOnProgressListener {
                dialogs.show()
                val progress = 100.0 * it.bytesTransferred / it.totalByteCount
                dialogs.progress = progress.toInt()
            }.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    voidCustomMediaScannerConnection(views.context, saveFilterImagePath)
                    dialogs.dismiss()
                    openImageFromSnackbar(views, imageFile)
                }
            }.addOnFailureListener {
                helper.timberLogE("local tem file not created ")
            }
        }catch (e:Exception){
            helper.timberLogE(e.message)
        }

    }

    //decode File into Bitmap and compress it
    override fun decodeSampledBitmapFromFile(imageFile: File): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFile.absolutePath, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateSampleSize(options, maxWidth, maxHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false

        var scaledBitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)

        //check the rotation of the image and display it properly
        val exif = ExifInterface(imageFile.absolutePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
        val matrix = Matrix()
        when (orientation) {
            6 -> {
                matrix.postRotate(90F)
            }
            3 -> {
                matrix.postRotate(180F)
            }
            8 -> {
                matrix.postRotate(270F)
            }
        }
        scaledBitmap = Bitmap.createBitmap(
            scaledBitmap,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height,
            matrix,
            true
        )
        return scaledBitmap
    }

    private fun openImageFromSnackbar(views: View, imageFile: File) {
        val snackbar = Snackbar
            .make(views, "Image saved to gallery!", Snackbar.LENGTH_LONG)
            .setAction("OPEN") {
                val i = Intent(Intent.ACTION_VIEW)
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val uri =
                    FileProvider.getUriForFile(
                        views.context,
                        views.context.resources.getString(R.string.package_name),
                        imageFile
                    )
                i.setDataAndType(uri, "image/*")
                views.context.startActivity(i)
            }
        snackbar.show()
    }

    private fun calculateSampleSize(
        options: BitmapFactory.Options,
        requiredWidth: Int,
        requiredHeight: Int
    ): Int {
        val height = options.outHeight
        val widht = options.outWidth
        var inSampleSize = 1

        if (height > requiredHeight || widht > requiredHeight) {
            val halfHeight = height / 2
            val halfWidth = widht / 2

            while ((halfHeight / inSampleSize) >= requiredHeight && (halfWidth / inSampleSize) >= requiredWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

   private fun voidCustomMediaScannerConnection(ctx: Context?, paths: String) {
        val directory = Environment.getExternalStorageDirectory()
        val passingFile = File(directory, paths)
        MediaScannerConnection.scanFile(ctx, arrayOf(passingFile.toString()), null) { path, uri ->
            Log.i("ExternalStorage", "Scanned $path:")
            Log.i("ExternalStorage", "-> uri=$uri")
        }
    }
}