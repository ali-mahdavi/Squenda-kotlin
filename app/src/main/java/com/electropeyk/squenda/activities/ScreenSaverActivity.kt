package com.electropeyk.squenda.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.electropeyk.squenda.R
import com.electropeyk.squenda.utils.Common
import kotlinx.android.synthetic.main.activity_screen_saver.*
import java.text.SimpleDateFormat
import java.util.*

class ScreenSaverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)

        setContentView(R.layout.activity_screen_saver)

        btn_home_screen_saver.setOnClickListener {
            val intent = Intent(this, MyHomeActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        btn_profile_screen_saver.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        btn_setting_screen_saver.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        img_click.setOnClickListener {

                finish()


        }
        setTime()
    }

    private fun setTime() {

        txt_time_screen_saver.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_screen_saver.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
                        }
                    }
                } catch (e: InterruptedException) {
                }

            }
        }

        thread.start()
        var dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val day = Common.days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]
        val month = Common.months[Calendar.getInstance().get(Calendar.MONTH)]
        txt_date_screen_saver.text = "$day,$month $dayOfMonth"

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
