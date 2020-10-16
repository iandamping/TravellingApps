package com.junemon.uploader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.junemon.core.presentation.PresentationConstant
import com.junemon.uploader.databinding.ActivityMainBinding

private const val IMMERSIVE_FLAG_TIMEOUT = 500L

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        // Before setting full screen flags, we must wait a bit to let UI settle; otherwise, we may
        // be trying to set app to immersive mode before it's ready and the flags do not stick
        binding.root.postDelayed({
            binding.root.systemUiVisibility = PresentationConstant.FLAGS_FULLSCREEN
        }, IMMERSIVE_FLAG_TIMEOUT)
    }
}
