package com.junemon.uploader.di.module

import com.junemon.uploader.utility.classes.CommonHelperImpl
import com.junemon.uploader.utility.classes.ImageUtilImpl
import com.junemon.uploader.utility.classes.IntentUtilImpl
import com.junemon.uploader.utility.classes.LoadImageHelperImpl
import com.junemon.uploader.utility.classes.PermissionUtilImpl
import com.junemon.uploader.utility.classes.RecyclerHelperImpl
import com.junemon.uploader.utility.classes.ViewHelperImpl
import com.junemon.uploader.utility.interfaces.CommonHelper
import com.junemon.uploader.utility.interfaces.ImageHelper
import com.junemon.uploader.utility.interfaces.IntentHelper
import com.junemon.uploader.utility.interfaces.LoadImageHelper
import com.junemon.uploader.utility.interfaces.PermissionHelper
import com.junemon.uploader.utility.interfaces.RecyclerHelper
import com.junemon.uploader.utility.interfaces.ViewHelper
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
    abstract fun bindsRecyclerViewHelper(recyclerHelper: RecyclerHelperImpl): RecyclerHelper

    @Binds
    abstract fun bindsLoadImageHelper(loadImageHelper: LoadImageHelperImpl): LoadImageHelper

    @Binds
    abstract fun bindsViewHelper(viewHelper: ViewHelperImpl): ViewHelper

    @Binds
    abstract fun bindsCommmonHelper(commonHelper: CommonHelperImpl): CommonHelper
}
