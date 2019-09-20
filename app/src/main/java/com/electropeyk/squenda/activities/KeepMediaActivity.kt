package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.R

import com.electropeyk.squenda.models.ResetClass
import com.electropeyk.squenda.models.TypeOfRest
import com.electropeyk.squenda.utils.Common
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_keep_media.*
import java.text.SimpleDateFormat
import java.util.*


class KeepMediaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keep_media)

        img_back_keep.setOnClickListener {
            val intent = Intent(this, DataAndStorageActivity::class.java)
            finish()
            startActivity(intent)
        }
        btn_home_keep.setOnClickListener {
            val intent = Intent(this, MyHomeActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        btn_profile_keep.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        btn_setting_keep.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        setTime()

        Paper.init(this)
        val restClass = Paper.book(Common.DATABASE).read<ResetClass>(Common.RESET_MOMORY)
        if (restClass.type == TypeOfRest.MONTH) {
            img_foreever_check.visibility = View.GONE
            img_week_check.visibility = View.GONE
            img_month_check.visibility = View.VISIBLE
        }
        if (restClass.type == TypeOfRest.WEEK) {
            img_foreever_check.visibility = View.GONE
            img_week_check.visibility = View.VISIBLE
            img_month_check.visibility = View.GONE
        }
        if (restClass.type == TypeOfRest.FOREVER) {
            img_foreever_check.visibility = View.VISIBLE
            img_week_check.visibility = View.GONE
            img_month_check.visibility = View.GONE
        }


        rl_forever.setOnClickListener {
            img_foreever_check.visibility = View.VISIBLE
            img_week_check.visibility = View.GONE
            img_month_check.visibility = View.GONE
            val cal = Calendar.getInstance()

            val resetClass = ResetClass(TypeOfRest.FOREVER, cal)
            Paper.book(Common.DATABASE).write(Common.RESET_MOMORY, resetClass)
        }
        rl_week.setOnClickListener {
            img_foreever_check.visibility = View.GONE
            img_week_check.visibility = View.VISIBLE
            img_month_check.visibility = View.GONE
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_WEEK, 6)
            val resetClass = ResetClass(TypeOfRest.WEEK, cal)
            Paper.book(Common.DATABASE).write(Common.RESET_MOMORY, resetClass)

        }

        rl_month.setOnClickListener {
            img_foreever_check.visibility = View.GONE
            img_week_check.visibility = View.GONE
            img_month_check.visibility = View.VISIBLE
            val cal = Calendar.getInstance()
            cal.add(Calendar.MONTH, 1)
            val resetClass = ResetClass(TypeOfRest.MONTH, cal)
            Paper.book(Common.DATABASE).write(Common.RESET_MOMORY, resetClass)
        }
    }
    private fun setTime() {
        txt_time_keep.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_keep.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
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
        txt_date_keep.text = "$day,$month $dayOfMonth"

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
