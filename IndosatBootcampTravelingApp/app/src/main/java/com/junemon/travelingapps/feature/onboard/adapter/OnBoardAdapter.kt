package com.junemon.travelingapps.feature.onboard.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.junemon.travelingapps.feature.culture.FragmentCulture
import com.junemon.travelingapps.feature.nature.FragmentNature
import com.junemon.travelingapps.feature.religious.FragmentReligious

/**
 * Created by Ian Damping on 03,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class OnBoardAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    private val pageCount: Int = 3

    override fun getItemCount(): Int {
        return pageCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentNature()
            1 -> FragmentCulture()
            else -> FragmentReligious()
        }
    }
}