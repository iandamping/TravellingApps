package com.junemon.core.di.module

import com.google.gson.Gson
import com.junemon.core.presentation.util.classes.CommonHelperImpl
import com.junemon.core.presentation.util.classes.ImageUtilImpl
import com.junemon.core.presentation.util.classes.IntentUtilImpl
import com.junemon.core.presentation.util.classes.LoadImageHelperImpl
import com.junemon.core.presentation.util.classes.PermissionUtilImpl
import com.junemon.core.presentation.util.classes.RecyclerHelperImpl
import com.junemon.core.presentation.util.classes.ViewHelperImpl
import com.junemon.core.presentation.util.interfaces.CommonHelper
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.PermissionHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Ian Damping on 16,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
val presentationModule = module {
    single {
        IntentUtilImpl(
            ioDispatcher = get(named("io")),
            mainDispatcher = get(named("main")),
            requestManager = get(),
            context = androidContext()
        ) as IntentHelper
    }

    single {
        ImageUtilImpl(
            ioDispatcher = get(named("io")),
            storagePlaceReference = get(),
            requestManager = get(),
            context = androidContext()
        ) as ImageHelper
    }

    single {
        PermissionUtilImpl(
            context = androidContext()
        ) as PermissionHelper
    }

    single {
        LoadImageHelperImpl(
            ioDispatcher = get(named("io")),
            mainDispatcher = get(named("main")),
            glideInstance = get(),
            requestManager = get(),
            requestOptions = get()
        ) as LoadImageHelper
    }
    single {
        ViewHelperImpl() as ViewHelper
    }

    single {
        RecyclerHelperImpl() as RecyclerHelper
    }

    single {
        CommonHelperImpl() as CommonHelper
    }

    single {
        Gson()
    }
}
