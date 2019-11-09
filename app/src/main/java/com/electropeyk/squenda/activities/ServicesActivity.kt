package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.R

class ServicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        Handler().postDelayed(Runnable {
            //here call the second method
            val intent = Intent(this, ScreenSaverActivity::class.java)
            // start your next activity
            startActivity(intent)
        }, 90000)

    }
}
