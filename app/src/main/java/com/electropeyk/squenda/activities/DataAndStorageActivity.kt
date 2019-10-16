package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.models.MetaFile
import com.electropeyk.squenda.models.ResetClass
import com.electropeyk.squenda.models.TypeOfRest
import com.electropeyk.squenda.models.TypeStorage
import com.electropeyk.squenda.utils.Common
import com.electropeyk.squenda.utils.Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST
import com.electropeyk.squenda.utils.Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_data_and_storage.*
import kotlinx.android.synthetic.main.activity_gallery.txt_photo_size
import kotlinx.android.synthetic.main.activity_gallery.txt_video_size
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.os.Environment.getExternalStorageDirectory




class DataAndStorageActivity : AppCompatActivity() {
    private var viedoSize = 0
    private var photoSize = 0
    private var isOpen: Boolean = false
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
        val availableString = Common.getAvailableInternalMemorySize();
        val totalString = Common.getTotalInternalMemorySize()
        val total = totalString.substring(0, totalString.length - 2)
        val available = availableString.substring(0, availableString.length - 2)

        val currentAvailble = Integer.parseInt(available) - viedoSize / 1024 - photoSize / 1204
        val storageMsg = currentAvailble.toString() + "MB of " + totalString + " Used"
        txt_available.text = storageMsg
        progress_data.max = Integer.parseInt(Common.ReplaceAllCammaToBlank(total))
        progress_data.progress = viedoSize/1024
        progress_data.secondaryProgress = viedoSize / 1024 + photoSize / 1204

        val typeStorage = Paper.book(Common.DATABASE).read<TypeStorage>(Common.PATH_TYPE)
        if (typeStorage != null) {
            if (typeStorage == TypeStorage.SDCARD)
                storage_type.text = "SD Card"
            else
                storage_type.text = "SQENDA"
        }
        val resetMemory = Paper.book(Common.DATABASE).read<ResetClass>(Common.RESET_MOMORY)
        if (resetMemory != null) {
            if (resetMemory.type == TypeOfRest.MONTH)
                txt_keep.text = "Month"
            else
                if (resetMemory.type == TypeOfRest.WEEK)
                    txt_keep.text = "Week"

        } else
            txt_keep.text = "Forever"

        fr_msg.setVisibility(View.GONE)

        txt_reset.setOnClickListener {
            fr_msg.setVisibility(View.VISIBLE)
            val RightSwipe = AnimationUtils.loadAnimation(this, com.electropeyk.squenda.R.anim.right_swipe)
            fr_msg.startAnimation(RightSwipe)
            isOpen = true
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (isOpen) {
                    val RightSwipe = AnimationUtils.loadAnimation(this, com.electropeyk.squenda.R.anim.left_swipe)
                    fr_msg.startAnimation(RightSwipe)
                    fr_msg.setVisibility(View.GONE)
                }

            }, 4000)
        }

        btn_check_rest.setOnClickListener {
            deletePhotos()
            deleteVideos()
            Common.RemoveContainDirectory("/storage/emulated/0/images")
            Common.RemoveContainDirectory("/data/user/0/com.electropeyk.squenda/app_images/")
            Common.RemoveContainDirectory("/storage/emulated/0/videos")
            Common.RemoveContainDirectory("/data/user/0/com.electropeyk.squenda/app_videos/")

            txt_photo_size.setText("0 KB")
            txt_video_size.setText("0 KB")
            progress_data.progress = 0
            progress_data.secondaryProgress = 0
            val RightSwipe = AnimationUtils.loadAnimation(this, com.electropeyk.squenda.R.anim.left_swipe)
            fr_msg.startAnimation(RightSwipe)
            fr_msg.setVisibility(View.GONE)
            isOpen = false

        }


    }


    private fun deletePhotos() {
        ABSOLUTE_PATH_NAMES_PHOTO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_PHOTO)
        if (ABSOLUTE_PATH_NAMES_PHOTO_LIST != null && ABSOLUTE_PATH_NAMES_PHOTO_LIST.size > 0) {
            var i = 0
            for (MetaFile in ABSOLUTE_PATH_NAMES_PHOTO_LIST) {
                val fdelete = File(MetaFile.path)
                if (fdelete.exists()) {
                    fdelete.delete()

                }
                i++

            }

            ABSOLUTE_PATH_NAMES_PHOTO_LIST = ArrayList<MetaFile>()
            Paper.book(Common.DATABASE).write(Common.ABSOLUTE_PATH_NAMES_PHOTO, ABSOLUTE_PATH_NAMES_PHOTO_LIST)
        }
    }

    private fun deleteVideos() {
        ABSOLUTE_PATH_NAMES_VIDEO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_VIDEO)
        if (ABSOLUTE_PATH_NAMES_VIDEO_LIST != null && ABSOLUTE_PATH_NAMES_VIDEO_LIST.size > 0) {
            var i = 0
            for (MetaFile in ABSOLUTE_PATH_NAMES_VIDEO_LIST) {
                val fdelete = File(MetaFile.path)
                if (fdelete.exists()) {

                    fdelete.delete()
                }
                i++

            }
            ABSOLUTE_PATH_NAMES_VIDEO_LIST = ArrayList<MetaFile>()
            Paper.book(Common.DATABASE).write(Common.ABSOLUTE_PATH_NAMES_VIDEO, ABSOLUTE_PATH_NAMES_VIDEO_LIST)
        }
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
        ABSOLUTE_PATH_NAMES_VIDEO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_VIDEO)
        ABSOLUTE_PATH_NAMES_PHOTO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_PHOTO)
        if (ABSOLUTE_PATH_NAMES_VIDEO_LIST != null && Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST.size > 0) {

            for (MetaFile in Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST) {
                val file = File(MetaFile.path)
                val file_size = Integer.parseInt((file.length() / 1024).toString())
                viedoSize += file_size
            }
            txt_video_size.setText("$viedoSize KB")
        }
        if (ABSOLUTE_PATH_NAMES_PHOTO_LIST != null && Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.size > 0) {

            for (MetaFile in ABSOLUTE_PATH_NAMES_PHOTO_LIST) {
                val file = File(MetaFile.path)
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





