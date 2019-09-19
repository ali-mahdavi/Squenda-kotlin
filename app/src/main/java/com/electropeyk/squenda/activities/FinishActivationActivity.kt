package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.R
import com.electropeyk.squenda.utils.Common
import kotlinx.android.synthetic.main.activity_finish_activation.*

class FinishActivationActivity : AppCompatActivity() {
    private val SPLASH_DURATION = 4500L
    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null
    val TAG: String = FinishActivationActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        setContentView(R.layout.activity_finish_activation)

        Common.hideKeyboard(this)

        txt_1.setText("1234")
        txt_2.setText("1234")
        txt_3.setText("1234")
        txt_4.setText("1234")
        txt_5.setText("1234")


        mHandler = Handler()
        mHandler?.postDelayed(mRunnable, SPLASH_DURATION)
        mRunnable = Runnable { dismissSplash() }


        rl_logo.setOnClickListener {
            val intent = Intent(this, FirstMenueActivity::class.java)
            finish()
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        mHandler?.postDelayed(mRunnable, SPLASH_DURATION)
    }

    override fun onPause() {
        super.onPause()
        mHandler?.removeCallbacks(mRunnable)
    }

    private fun dismissSplash() {
        val intent = Intent(this, FirstMenueActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
        else
            showSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}
