package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.R
import kotlinx.android.synthetic.main.activity_all_devices.*
import kotlinx.android.synthetic.main.activity_call.*
import kotlinx.android.synthetic.main.activity_media.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class CallActivity : AppCompatActivity() {
    private var inputValue: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onCreate(savedInstanceState)
        setContentView(com.electropeyk.squenda.R.layout.activity_call)
        img_back_call.setOnClickListener{
            val intent = Intent(this, AllDevicesActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        btn_1.setOnClickListener {
            inputValue += "1"
            txt_call.setText(inputValue)
        }

        btn_2.setOnClickListener {
            inputValue += "2"
            txt_call.setText(inputValue)
        }

        btn_3.setOnClickListener {
            inputValue += "3"
            txt_call.setText(inputValue)
        }

        btn_4.setOnClickListener {
            inputValue += "4"
            txt_call.setText(inputValue)
        }
        btn_5.setOnClickListener {
            inputValue += "5"
            txt_call.setText(inputValue)
        }
        btn_6.setOnClickListener {
            inputValue += "6"
            txt_call.setText(inputValue)
        }
        btn_7.setOnClickListener {
            inputValue += "7"
            txt_call.setText(inputValue)
        }
        btn_8.setOnClickListener {
            inputValue += "8"
            txt_call.setText(inputValue)
        }
        btn_9.setOnClickListener {
            inputValue += "9"
            txt_call.setText(inputValue)
        }
        btn_0.setOnClickListener {
            inputValue += "0"
            txt_call.setText(inputValue)
        }
        btn_sharp.setOnClickListener {
            inputValue += "#"
            txt_call.setText(inputValue)
        }
        btn_star.setOnClickListener {
            inputValue += "*"
            txt_call.setText(inputValue)
        }
        btn_del.setOnClickListener {
            if(inputValue!!.length>0){
                inputValue=   txt_call.getText().toString().substring(0,txt_call.length()-1)
                txt_call.setText(inputValue)
            }
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

    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

}
