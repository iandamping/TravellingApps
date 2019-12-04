package com.junemon.travelingapps.presentation.di

import com.junemon.travelingapps.presentation.util.classes.ImageUtil
import com.junemon.travelingapps.presentation.util.classes.PermissionUtil
import com.junemon.travelingapps.presentation.util.interfaces.ImageHelperResult
import com.junemon.travelingapps.presentation.util.interfaces.PermissionHelperResult
import org.koin.dsl.module

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

val presentationModule = module {
    factory { ImageUtil() as ImageHelperResult }
    factory { PermissionUtil() as PermissionHelperResult }
}