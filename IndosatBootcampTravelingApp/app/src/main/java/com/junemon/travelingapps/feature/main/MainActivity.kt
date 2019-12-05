package com.junemon.travelingapps.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.ActivityMainBinding
import com.junemon.travelingapps.databinding.FragmentHomeBinding
import com.junemon.travelingapps.vm.PlaceViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val placeVm: PlaceViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        placeVm.setCache()
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }
}
