package com.junemon.travelingapps.util.classes

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.junemon.core.di.dispatcher.IoDispatcher
import com.junemon.core.di.dispatcher.MainDispatcher
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class LoadImageHelperImpl @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val glideInstance: Glide,
    private val requestManager: RequestManager,
    private val requestOptions: RequestOptions
) :
    LoadImageHelper {

    override fun ImageView.loadWithGlideSmall(url: String?) {
        requestManager.load(url).apply(requestOptions.override(150, 150))
            .thumbnail(0.25f)
            .into(this)
    }

    override fun ImageView.loadWithGlideCustomSize(url: String?, width: Int, height: Int) {
        requestManager.load(url).apply(requestOptions.override(width, height))
            .thumbnail(0.25f)
            .into(this)
    }

    override fun ImageView.loadWithGlide(url: String?) {
        requestManager.load(url).apply(requestOptions)
            .thumbnail(0.25f).into(this)
    }

    override fun ImageView.loadWithGlide(drawable: Drawable) {
        requestManager.load(drawable).into(this)
    }

    override fun ImageView.loadWithGlide(bitmap: Bitmap) {
        requestManager.load(bitmap).into(this)
    }

    override fun ImageView.loadWithGlide(file: File) {
        requestManager.load(file).into(this)
    }

    override suspend fun clearMemory() = withContext(mainDispatcher) {
        glideInstance.clearMemory()
    }

    override suspend fun clearDiskCache() = withContext(ioDispatcher) {
        glideInstance.clearDiskCache()
    }
}
