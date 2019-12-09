package com.junemon.travelingapps.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.ActivityMainBinding
import com.junemon.travelingapps.presentation.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    /*override fun onBackPressed() {
        super.onBackPressed()
        // only way to remove glitch image left when exiting apps
        finish()
    }*/
}
