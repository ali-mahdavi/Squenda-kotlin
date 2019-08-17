package com.electropeyk.squenda.activities
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.R
import com.electropeyk.squenda.utils.Common
import kotlinx.android.synthetic.main.activity_all_devices.*
import kotlinx.android.synthetic.main.activity_my_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MyHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_home)
        rl_all.setOnClickListener {
            val intent = Intent(this, AllDevicesActivity::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        btn_setting_home.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        btn_profile_home.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        btn_notification.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        rl_living_room.setOnClickListener {
            val intent = Intent(this, LivingRoomActivity::class.java)
            // start your next activity
            intent.putExtra("title", "Living Room")
            intent.putExtra("devices", "9")
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        rl_bathroom.setOnClickListener {
            val intent = Intent(this, LivingRoomActivity::class.java)
            // start your next activity
            intent.putExtra("title", "Bath Room")
            intent.putExtra("devices", "2")

            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        rl_bedroom.setOnClickListener {
            val intent = Intent(this, LivingRoomActivity::class.java)
            intent.putExtra("title", "Bed Room")

            intent.putExtra("devices", "5")
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        rl_kitchen.setOnClickListener {
            val intent = Intent(this, LivingRoomActivity::class.java)
            intent.putExtra("title", "Kitchen Room")
            intent.putExtra("devices", "3")

            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        img_back_home.setOnClickListener {
            val intent = Intent(this, FirstMenueActivity::class.java)
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
                            txt_time_home.text= SimpleDateFormat("HH:mm", Locale.US).format( Date())
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
        txt_date_home.text= "$day,$month $lastTwoDigits"


    }

    override fun onBackPressed() {
        val intent = Intent(this, FirstMenueActivity::class.java)
        // start your next activity
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
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
