package com.junemon.travelingapps.di.factory

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Ian Damping on 17,April,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)