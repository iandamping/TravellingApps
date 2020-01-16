package com.junemon.core.presentation.di

import androidx.lifecycle.ViewModelProvider
import com.junemon.core.presentation.di.factory.ViewModelFactory
import com.junemon.core.presentation.util.classes.CommonHelperImpl
import com.junemon.core.presentation.util.classes.ImageUtilImpl
import com.junemon.core.presentation.util.classes.IntentUtilImpl
import com.junemon.core.presentation.util.classes.LoadImageHelperImpl
import com.junemon.core.presentation.util.classes.PermissionUtilImpl
import com.junemon.core.presentation.util.classes.ViewHelperImpl
import com.junemon.core.presentation.util.interfaces.CommonHelper
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.PermissionHelper
import com.junemon.core.presentation.util.interfaces.RecyclerHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.core.presentation.util.classes.RecyclerHelperImpl
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

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
