package com.junemon.travelingapps.di.module

import com.junemon.travelingapps.util.classes.CommonHelperImpl
import com.junemon.travelingapps.util.classes.ImageUtilImpl
import com.junemon.travelingapps.util.classes.IntentUtilImpl
import com.junemon.travelingapps.util.classes.LoadImageHelperImpl
import com.junemon.travelingapps.util.classes.PermissionUtilImpl
import com.junemon.travelingapps.util.classes.ViewHelperImpl
import com.junemon.travelingapps.util.interfaces.CommonHelper
import com.junemon.travelingapps.util.interfaces.ImageHelper
import com.junemon.travelingapps.util.interfaces.IntentHelper
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.interfaces.PermissionHelper
import com.junemon.travelingapps.util.interfaces.ViewHelper
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

@Module
abstract class PresentationModule {

    @Binds
    abstract fun bindsIntentUtil(intentUtilImpl: IntentUtilImpl): IntentHelper

    @Binds
    abstract fun bindsImageUtil(imageUtilImpl: ImageUtilImpl): ImageHelper

    @Binds
    abstract fun bindsPermissionHelperUtil(permissionUtil: PermissionUtilImpl): PermissionHelper

    @Binds
    abstract fun bindsLoadImageHelper(loadImageHelper: LoadImageHelperImpl): LoadImageHelper

    @Binds
    abstract fun bindsViewHelper(viewHelper: ViewHelperImpl): ViewHelper

    @Binds
    abstract fun bindsCommmonHelper(commonHelper: CommonHelperImpl): CommonHelper
}
