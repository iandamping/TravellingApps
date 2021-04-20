package com.junemon.travelingapps.di.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Ian Damping on 17,April,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Singleton
class DefaultFragmentFactory
@Inject constructor(
    private val providerMap: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)

        val creator = providerMap[fragmentClass] ?: providerMap.entries.firstOrNull {
            fragmentClass.isAssignableFrom(it.key)
        }?.value

        return creator?.get() ?: super.instantiate(classLoader, className)
    }
}