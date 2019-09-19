package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.utils.Common
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_data_and_storage.*
import kotlinx.android.synthetic.main.activity_gallery.txt_photo_size
import kotlinx.android.synthetic.main.activity_gallery.txt_video_size
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class DataAndStorageActivity : AppCompatActivity() {
    private var viedoSize = 0
    private var photoSize = 0
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.electropeyk.squenda.R.layout.activity_data_and_storage)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        rl_keep_media_data.setOnClickListener {
            val intent = Intent(this, KeepMediaActivity::class.java)
            finish()
            startActivity(intent)
        }
        rl_storage_data.setOnClickListener {
            val intent = Intent(this, StorageActivity::class.java)
            finish()
            startActivity(intent)
        }
        Paper.init(this)
        setSize()

        img_back_data_storage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            finish()
            startActivity(intent)
        }

        setTime()

        btn_home_data_storage.setOnClickListener {
            val intent = Intent(this, MyHomeActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        btn_profile_data_storage.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        btn_setting_data_storage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }


        val available = Common.getAvailableInternalMemorySize();

        val total = Common.getTotalInternalMemorySize()

        val storageMsg = available + " of " + total + " Used"

        txt_available.text = storageMsg

        /* progress_data.setProgress(41,true)
         progress_data.secondaryProgress = 31
 */
        // progress_data.setProgress( photoSize/100,true)
        val variable = total.substring(0, total.length - 2)

        progress_data.max = Integer.parseInt(variable)
        progress_data.progress = viedoSize / 1024
        progress_data.secondaryProgress = photoSize / 1024


    }


    private fun setTime() {
        txt_time_data_storage.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_data_storage.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
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
        txt_date_data_storage.text = "$day,$month $dayOfMonth"

    }


    private fun setSize() {
        txt_video_size.setText("0 KB")
        Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_VIDEO)
        Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_PHOTO)
        if (Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST != null && Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST.size > 0) {

            for (path in Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST) {
                val file = File(path)
                val file_size = Integer.parseInt((file.length() / 1024).toString())
                viedoSize += file_size
            }
            txt_video_size.setText("$viedoSize KB")
        }
        if (Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST != null && Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.size > 0) {

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

