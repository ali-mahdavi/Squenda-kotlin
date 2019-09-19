package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.R
import com.electropeyk.squenda.utils.Common
import kotlinx.android.synthetic.main.activity_enter_code.*

class EnterCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        setContentView(R.layout.activity_enter_code)
        fr_msg.setVisibility(View.GONE)
        btn_active.setOnClickListener {
            if (!isValidate()) {
                fr_msg.setVisibility(View.VISIBLE)
                val RightSwipe = AnimationUtils.loadAnimation(this, R.anim.right_swipe)
                fr_msg.startAnimation(RightSwipe)
            } else {
                val intent = Intent(this, FinishActivationActivity::class.java)
                finish()
                startActivity(intent)
            }
        }

        btn_close.setOnClickListener {

            val RightSwipe = AnimationUtils.loadAnimation(this, R.anim.left_swipe)
            fr_msg.startAnimation(RightSwipe)
            fr_msg.setVisibility(View.GONE)
            edit_1.background = getDrawable(R.drawable.rectangle_active_code)
            edit_2.background = getDrawable(R.drawable.rectangle_active_code)
            edit_3.background = getDrawable(R.drawable.rectangle_active_code)
            edit_4.background = getDrawable(R.drawable.rectangle_active_code)
            edit_5.background = getDrawable(R.drawable.rectangle_active_code)
        }


    }

    private fun isValidate(): Boolean {

        if (edit_1.text.isEmpty() ||
            edit_2.text.isEmpty() ||
            edit_3.text.isEmpty() ||
            edit_4.text.isEmpty() ||
            edit_5.text.isEmpty()
        ) {
            txt_err.text = getString(R.string.activation_code_entry_msg)
            return false
        }

        if (


            Common.compatreString(edit_1.text.trim().toString(), "1234") &&
            Common.compatreString(edit_2.text.trim().toString(), "1234") &&
            Common.compatreString(edit_3.text.trim().toString(), "1234") &&
            Common.compatreString(edit_4.text.trim().toString(), "1234") &&
            Common.compatreString(edit_5.text.trim().toString(), "1234")


        ) {

            return true
        } else {
            txt_err.text = getString(R.string.activation_code_err_msg)
            edit_1.background = getDrawable(R.drawable.rectangle_active_code_fail)
            edit_2.background = getDrawable(R.drawable.rectangle_active_code_fail)
            edit_3.background = getDrawable(R.drawable.rectangle_active_code_fail)
            edit_4.background = getDrawable(R.drawable.rectangle_active_code_fail)
            edit_5.background = getDrawable(R.drawable.rectangle_active_code_fail)
        }
        return false

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
