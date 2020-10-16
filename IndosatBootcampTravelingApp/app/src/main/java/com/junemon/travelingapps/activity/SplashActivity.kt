package com.junemon.travelingapps.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

import androidx.lifecycle.lifecycleScope as androidxLifecycleScope
import org.koin.androidx.scope.lifecycleScope as koinLifecycleScope

/**
 * Created by Ian Damping on 09,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val  loadImageHelper: LoadImageHelper by inject()

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
                resources.getDrawable(R.drawable.ic_splash, null)
            )
        }
        runDelayed {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun runDelayed(call: () -> Unit) {
        androidxLifecycleScope.launch {
            delay(500L)
            call.invoke()
        }
    }
}