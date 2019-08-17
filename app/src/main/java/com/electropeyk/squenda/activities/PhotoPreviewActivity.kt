package com.electropeyk.squenda.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.electropeyk.squenda.R
import com.electropeyk.squenda.utils.Common
import kotlinx.android.synthetic.main.activity_all_devices.*
import kotlinx.android.synthetic.main.activity_photo_list.*
import kotlinx.android.synthetic.main.activity_photo_preview.*
import kotlinx.android.synthetic.main.activity_setting.*
import java.text.SimpleDateFormat
import java.util.*

class PhotoPreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_preview)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        img_back_photo_preview.setOnClickListener{
            val intent = Intent(this, PhotoListActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_photo_preview.text= SimpleDateFormat("HH:mm", Locale.US).format( Date())
                        }
                    }
                } catch (e: InterruptedException) {
                }

            }
        }

        thread.start()
        val lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100
        val day = Common.days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]
        val month = Common.months[Calendar.getInstance().get(Calendar.MONTH) - 1]
        txt_date_photo_preview.text= "$day,$month $lastTwoDigits"

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
