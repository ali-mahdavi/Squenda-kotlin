package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.R
import com.electropeyk.squenda.utils.Common
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_all_devices.*
import kotlinx.android.synthetic.main.activity_call.*
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.activity_notification.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class GalleryActivity : AppCompatActivity() {
    val TAG :String =GalleryActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.electropeyk.squenda.R.layout.activity_gallery)
        btn_home_gallary.setOnClickListener {
            val intent = Intent(this, MyHomeActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }
        btn_setting_gallary.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }
        btn_profile_gallary.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        Paper.init(this)
        setSize()
        rl_video.setOnClickListener(){
            if (Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST == null || Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST.size == 0) {
                Toast.makeText(this, "There is not any video", Toast.LENGTH_LONG).show()
            } else {
                val videoList = Intent(this, VideoListActivity::class.java)
                startActivity(videoList)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
        rl_photo.setOnClickListener(){
            if (Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST == null || Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.size == 0) {
                Toast.makeText(this, "There is not any photo ", Toast.LENGTH_LONG).show()

            } else {
                Log.i(TAG, "Photo size is: " + Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.size)
                val cameraList = Intent(this, PhotoListActivity::class.java)
                startActivity(cameraList)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }


        img_back_gallary.setOnClickListener{
            val intent = Intent(this, MediaActivity::class.java)
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
                            txt_time_gallary.text= SimpleDateFormat("HH:mm", Locale.US).format( Date())
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
        txt_date_gallary.text= "$day,$month $lastTwoDigits"
    }

    private fun setSize() {
        txt_video_size.setText("0 KB")
        Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_VIDEO)
        if (Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST != null && Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST.size > 0) {
            var videoSize = 0
            for (path in Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST) {
                val file = File(path)
                val file_size = Integer.parseInt((file.length() / 1024).toString())
                videoSize += file_size
            }
            txt_video_size.setText("$videoSize KB")
        }
        if (Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST != null && Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.size > 0) {
            var photoSize = 0
            for (path in Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST) {
                val file = File(path)
                val file_size = Integer.parseInt((file.length() / 1024).toString())
                photoSize += file_size
            }
            txt_photo_size.setText("$photoSize KB")
        }


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
