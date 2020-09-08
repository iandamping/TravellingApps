package com.junemon.dummy_bottomnav_navdrawer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junemon.dummy_bottomnav_navdrawer.databinding.Fragment1Binding

/**
 * Created by Ian Damping on 24,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }


}