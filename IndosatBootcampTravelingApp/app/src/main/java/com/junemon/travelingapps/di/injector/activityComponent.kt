package com.junemon.travelingapps.di.injector

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.junemon.travelingapps.PlaceApplication

fun FragmentActivity.activityComponent() =
    (application as PlaceApplication).provideApplicationComponent().getActivityComponent()
//
// fun Service.serviceComponent() =
//     (application as MainApplication).provideApplicationComponent().getServiceComponent()
//
fun Fragment.appComponent() =
    (requireActivity().application as PlaceApplication).provideApplicationComponent()
//
// fun Fragment.applicationComponent() =
//     (requireActivity().application as MainApplication).provideApplicationComponent()
//
// fun FragmentActivity.preferenceHelperInjector() =
//     (application as MainApplication).provideApplicationComponent().getPreferenceHelper()
//
// fun Fragment.sharedNotificationFlowInjector() =
//     (requireActivity().application as MainApplication).provideApplicationComponent().getSharedNotificationFlow()
//
// fun Service.sharedNotificationFlowInjector() =
//     (application as MainApplication).provideApplicationComponent().getSharedNotificationFlow()