package com.junemon.travelingapps.feature.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.travelingapps.databinding.FragmentOnboardBinding
import com.junemon.travelingapps.feature.onboard.adapter.OnBoardAdapter

/**
 * Created by Ian Damping on 03,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FragmentOnBoard : BaseFragment() {

    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding!!

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
    }

    private fun FragmentOnboardBinding.initView() {
        btnSearchMain.setOnClickListener {
            findNavController()
                .navigate(FragmentOnBoardDirections.actionFragmentOnBoardToSearchFragment())
        }

        val tabTitle = arrayOf("Nature", "Culture", "Religious")

        tabMainPage.setupWithViewPager(vpMainPage)
        vpMainPage.adapter = OnBoardAdapter(tabTitle,childFragmentManager)

    }
}