package com.junemon.travelingapps.feature.onboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.junemon.travelingapps.feature.culture.FragmentCulture
import com.junemon.travelingapps.feature.nature.FragmentNature
import com.junemon.travelingapps.feature.religious.FragmentReligious

/**
 * Created by Ian Damping on 03,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class OnBoardAdapter(private val tabTitle: Array<String>,fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val pageCount: Int = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FragmentNature()
            1 -> FragmentCulture()
            else -> FragmentReligious()
        }
    }

    override fun getCount(): Int {
        return pageCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitle[position]
    }
}

