package com.junemon.core.di.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.junemon.core.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Ian Damping on 23,June,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

val glideModule = module {
    single { provideRequestOptions() }
    single { provideRequestManager(application = androidContext(), requestOptions = get()) }
    single { provideGlideInstance(application = androidContext()) }
}

private fun provideRequestManager(
    application: Context,
    requestOptions: RequestOptions
): RequestManager {
    return Glide.with(application)
        .setDefaultRequestOptions(requestOptions)
}

private fun provideGlideInstance(application: Context):Glide {
    return Glide.get(application)
}

private fun provideRequestOptions(): RequestOptions {
    return RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .format(DecodeFormat.PREFER_RGB_565)
        .placeholder(R.drawable.empty_image)
        .error(R.drawable.no_data)
}