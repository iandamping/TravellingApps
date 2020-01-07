package com.junemon.travelingapps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.junemon.travelingapps.R
import com.junemon.travelingapps.coreComponent
import com.junemon.travelingapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val activityComponent by lazy { coreComponent().getActivityComponent().create() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }
}
