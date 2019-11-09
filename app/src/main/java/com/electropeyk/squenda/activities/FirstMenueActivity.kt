package com.electropeyk.squenda.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.electropeyk.squenda.utils.Common.days
import com.electropeyk.squenda.utils.Common.months
import kotlinx.android.synthetic.main.activity_first_menue.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.ActivityCompat.startActivityForResult
import android.net.Uri
import android.Manifest.permission
import androidx.core.os.HandlerCompat.postDelayed
import android.R.string.no
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

class FirstMenueActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        setContentView(com.electropeyk.squenda.R.layout.activity_first_menue)


        btn_home_first.setOnClickListener {
            val intent = Intent(this, MyHomeActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }
        btn_setting_first.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }
        btn_profile_first.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }

        btn_location_first.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }

        img_back_first.setOnClickListener{
            finish()
        }

        if(!checkPermissionFromDevice())
            requestPermission()
        txt_time_first.text= SimpleDateFormat("HH:mm", Locale.US).format( Date())
        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_first.text= SimpleDateFormat("HH:mm", Locale.US).format( Date())
                        }
                    }
                } catch (e: InterruptedException) {
                }

            }
        }

        thread.start()

       var dayOfMonth=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val day = days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]
        val month = months[Calendar.getInstance().get(Calendar.MONTH)]
        txt_date_first.text= "$day,$month $dayOfMonth"

        Handler().postDelayed(Runnable {
            //here call the second method
            val intent = Intent(this, ScreenSaverActivity::class.java)
            // start your next activity
            startActivity(intent)
        }, 90000)


    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_SETTINGS
            ),
            REQUEST_PERMISSION_CODE
        )
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermissionFromDevice(): Boolean {
        val write_external_storage_result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        val camera_result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val write_setting:Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            write_setting = Settings.System.canWrite(this);
        } else {
            write_setting = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (write_setting) {
            return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                    record_audio_result == PackageManager.PERMISSION_GRANTED &&
                    camera_result == PackageManager.PERMISSION_GRANTED

        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + this.getPackageName())
                this.startActivityForResult(intent, 11)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_SETTINGS),
                    11
                )
            }
        }
        return true


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
