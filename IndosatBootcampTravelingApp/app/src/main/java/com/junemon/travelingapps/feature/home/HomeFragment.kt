package com.junemon.travelingapps.feature.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentHomeBinding
import com.junemon.travelingapps.feature.home.slideradapter.HomeSliderAdapter
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.travelingapps.presentation.model.mapCacheToPresentation
import com.junemon.travelingapps.vm.PlaceViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragment() {
    private lateinit var binders: FragmentHomeBinding
    private val placeVm:PlaceViewModel by viewModel()
    private var mHandler: Handler = Handler()
    private var pageSize: Int? = 0
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binders = this
            initView()
        }
        return binding.root
    }

    private fun FragmentHomeBinding.initView() {
        this.apply {
            btnCreate.setOnClickListener { it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUploadFragment()) }
            initViewPager()
        }
    }

    private fun FragmentHomeBinding.initViewPager(){
        apply {
            placeVm.getCache().observe(viewLifecycleOwner, Observer {
                pageSize = if (it.size > 10) {
                    10
                } else it.size

                vpPlaceRandom.adapter = HomeSliderAdapter(it.mapCacheToPresentation())
                indicator.setViewPager(vpPlaceRandom)
            })
        }
    }
    private fun slideRunnable(binding: FragmentHomeBinding) = object : Runnable {
        override fun run() {
            if (currentPage == pageSize) {
                currentPage = 0
            }
            binding.vpPlaceRandom.setCurrentItem(currentPage++, true)
            mHandler.postDelayed(this, 4000L)
        }
    }

    override fun onStart() {
        super.onStart()
        if (::binders.isInitialized) mHandler.postDelayed(slideRunnable(binders),4000L)
    }

    override fun onStop() {
        super.onStop()
        if (::binders.isInitialized) mHandler.removeCallbacks(slideRunnable(binders))
    }
}