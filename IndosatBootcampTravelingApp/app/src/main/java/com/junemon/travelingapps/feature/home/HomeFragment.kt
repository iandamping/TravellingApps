package com.junemon.travelingapps.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentHomeBinding
import com.junemon.travelingapps.presentation.base.BaseFragment

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment:BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        binding.apply {
            initView()
        }
        return binding.root
    }


    private fun FragmentHomeBinding.initView(){
        this.apply {
            btnCreate.setOnClickListener { it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUploadFragment()) }
        }
    }

}