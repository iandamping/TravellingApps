package com.junemon.travelingapps.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.junemon.travelingapps.R
import com.junemon.travelingapps.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by Ian Damping on 09,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SplashActivity : BaseActivity() {
    private var mDelayHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewHelper.run { this@SplashActivity.fullScreenAnimation() }
        setContentView(R.layout.activity_splash)
        loadingImageHelper.run {
            splashImage.loadWithGlide(ContextCompat.getDrawable(this@SplashActivity, R.drawable.samarinda_logo)!!) }
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, 3000L)
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
        mDelayHandler?.removeCallbacks(mRunnable)
    }
}