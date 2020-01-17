package com.junemon.travelingapps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.junemon.travelingapps.PlaceApplication
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val activityComponent by lazy { (application as PlaceApplication).activityComponent }
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }
}
