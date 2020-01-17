package com.junemon.travelingapps.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.R
import com.junemon.travelingapps.activityComponent
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 09,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private val mDelayHandler: Handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadImageHelper.run {
            splashImage.loadWithGlide(
                ContextCompat.getDrawable(
                    this@SplashActivity,
                    R.drawable.samarinda_logo
                )!!
            )
        }
        mDelayHandler.postDelayed(mRunnable, 3000L)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, splashImage, getString(
                    R.string.transition_name
                )
            )
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, activityOptionsCompat.toBundle())
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        mDelayHandler.removeCallbacks(mRunnable)
    }
}