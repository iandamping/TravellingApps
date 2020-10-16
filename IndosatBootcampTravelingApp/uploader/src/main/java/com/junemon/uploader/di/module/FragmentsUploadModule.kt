package com.junemon.uploader.di.module

import com.junemon.uploader.feature.upload.UploadFragment
import org.koin.dsl.module

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
val fragmentModule = module {
    scope<UploadFragment> {
        uploadViewModelInjector()
    }
}