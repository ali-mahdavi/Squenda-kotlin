package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.R
import com.electropeyk.squenda.models.TypeStorage
import com.electropeyk.squenda.utils.Common
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*

class StorageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)

        img_back_storage.setOnClickListener {
            val intent = Intent(this, DataAndStorageActivity::class.java)
            finish()
            startActivity(intent)
        }

        btn_home_storage.setOnClickListener {
            val intent = Intent(this, MyHomeActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        btn_profile_storage.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        btn_setting_storage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }



        setTime()



        Paper.init(this)

        val storageType = Paper.book(Common.DATABASE).read<TypeStorage>(Common.PATH_TYPE)
        if (storageType == TypeStorage.SQENDA) {
            img_sdcard_check.visibility = View.GONE
            img_sqenda_check.visibility = View.VISIBLE
        } else {
            img_sdcard_check.visibility = View.VISIBLE
            img_sqenda_check.visibility = View.GONE
        }


        rl_sqenda.setOnClickListener {
            img_sdcard_check.visibility = View.GONE
            img_sqenda_check.visibility = View.VISIBLE
            Paper.book(Common.DATABASE).write(Common.PATH_TYPE, TypeStorage.SQENDA)
        }

        rl_sdcard.setOnClickListener {
            img_sdcard_check.visibility = View.VISIBLE
            img_sqenda_check.visibility = View.GONE
            Paper.book(Common.DATABASE).write(Common.PATH_TYPE, TypeStorage.SDCARD)
        }

        rl_copy_all_to_sd.setOnClickListener {


        }


    }

    private fun setTime() {

        txt_time_storage.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_storage.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
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
        txt_date_storage.text = "$day,$month $dayOfMonth"

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
