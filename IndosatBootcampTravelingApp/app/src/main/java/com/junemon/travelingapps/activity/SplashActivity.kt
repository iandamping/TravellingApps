package com.junemon.travelingapps.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.ActivityMainBinding
import com.junemon.travelingapps.databinding.ActivitySplashBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 09,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SplashActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadImageHelper.run {
            splashImage.loadWithGlide(
                resources.getDrawable(R.drawable.samarinda_logo, null)
            )
        }
        runDelayed {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun runDelayed(call: () -> Unit) {
        lifecycleScope.launch {
            delay(500L)
            call.invoke()
        }
    }
}