package com.electropeyk.squenda.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.SparseIntArray
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.electropeyk.squenda.R
import com.electropeyk.squenda.adpter.VideoPagerAdapter
import com.electropeyk.squenda.utils.Common

import com.tmall.ultraviewpager.UltraViewPager
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_video_preview.*
import java.text.SimpleDateFormat
import java.util.*

class VideoPreviewActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener,
    AdapterView.OnItemSelectedListener {
    private var ultraViewPager: UltraViewPager? = null
    private var adapter: PagerAdapter? = null
    private var indicatorStyle: Spinner? = null
    private var indicatorGravityHor: Spinner? = null
    private var indicatorGravityVer: Spinner? = null
    private var indicatorBuildBtn: Button? = null
    private var loopCheckBox: CheckBox? = null
    private var autoScrollCheckBox: CheckBox? = null
    private var gravity_hor: Int = 0
    private var gravity_ver: Int = 0
    private var gravity_indicator: UltraViewPager.Orientation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_preview)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        Paper.init(this)
        Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_VIDEO)
        ultraViewPager = findViewById(R.id.ultra_viewpager) as UltraViewPager
        val extras :Bundle
        extras= getIntent().getExtras()!!
        ultraViewPager!!.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
        adapter = VideoPagerAdapter(this,true, Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST)
        ultraViewPager!!.setAdapter(adapter)
        ultraViewPager!!.setMultiScreen(0.6f)
        ultraViewPager!!.setItemRatio(1.0)
        ultraViewPager!!.setRatio(2.0f)
        ultraViewPager!!.setMaxHeight(800)
        ultraViewPager!!.setCurrentItem(extras.getInt("position"))
        ultraViewPager!!.disableAutoScroll()
        ultraViewPager!!.setAutoMeasureHeight(true)
        gravity_indicator = UltraViewPager.Orientation.HORIZONTAL
        ultraViewPager!!.setPageTransformer(false, UltraDepthScaleTransformer())

        initUI()
          img_back_video_preview.setOnClickListener{
            val intent = Intent(this, VideoListActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        txt_time_video_preview.text= SimpleDateFormat("HH:mm", Locale.US).format( Date())

        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_video_preview.text= SimpleDateFormat("HH:mm", Locale.US).format( Date())
                        }
                    }
                } catch (e: InterruptedException) {
                }

            }
        }

        thread.start()
        var dayOfMonth=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val day = Common.days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]
        val month = Common.months[Calendar.getInstance().get(Calendar.MONTH)]
        txt_date_video_preview.text= "$day,$month $dayOfMonth"


    }

    private fun initUI() {
        indicatorStyle = findViewById(R.id.indicator) as Spinner
        indicatorGravityHor = findViewById(R.id.indicator_gravity_hor) as Spinner
        indicatorGravityVer = findViewById(R.id.indicator_gravity_ver) as Spinner

        val indicatorAdapter =
            ArrayAdapter.createFromResource(this, R.array.indicator_style, android.R.layout.simple_spinner_item)
        indicatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        indicatorStyle!!.setAdapter(indicatorAdapter)

        val indicatorGraHorAdapter =
            ArrayAdapter.createFromResource(this, R.array.indicator_gravity_hor, android.R.layout.simple_spinner_item)
        indicatorGraHorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        indicatorGravityHor!!.setAdapter(indicatorGraHorAdapter)

        val indicatorGraVerAdapter =
            ArrayAdapter.createFromResource(this, R.array.indicator_gravity_ver, android.R.layout.simple_spinner_item)
        indicatorGraVerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        indicatorGravityVer!!.setAdapter(indicatorGraVerAdapter)

        indicatorStyle!!.setOnItemSelectedListener(this)
        indicatorGravityHor!!.setOnItemSelectedListener(this)
        indicatorGravityVer!!.setOnItemSelectedListener(this)

        loopCheckBox = findViewById(R.id.loop) as CheckBox
        loopCheckBox!!.setOnCheckedChangeListener(this)

        autoScrollCheckBox = findViewById(R.id.autoscroll) as CheckBox
        autoScrollCheckBox!!.setOnCheckedChangeListener(this)

        indicatorBuildBtn = findViewById(R.id.indicator_build) as Button
        indicatorBuildBtn!!.setOnClickListener(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        if (ultraViewPager!!.getIndicator() == null) {
            ultraViewPager!!.initIndicator()
            ultraViewPager!!.getIndicator().setOrientation(gravity_indicator)
        }
        if (parent === indicatorStyle) {
            when (position) {
                0 -> ultraViewPager!!.disableIndicator()
                1 -> {
                    ultraViewPager!!.getIndicator().setFocusResId(0).setNormalResId(0)
                    ultraViewPager!!.getIndicator().setFocusColor(Color.GREEN).setNormalColor(Color.WHITE)
                        .setRadius(
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                5f,
                                resources.displayMetrics
                            ).toInt()
                        )
                }
                2 -> ultraViewPager!!.getIndicator().setFocusResId(R.mipmap.tm_biz_lifemaster_indicator_selected)
                    .setNormalResId(R.mipmap.tm_biz_lifemaster_indicator_normal)
                3 -> {
                }
            }
        }
        if (parent === indicatorGravityHor) {
            when (position) {
                0 -> gravity_hor = Gravity.LEFT
                1 -> gravity_hor = Gravity.RIGHT
                2 -> gravity_hor = Gravity.CENTER_HORIZONTAL
            }
            if (ultraViewPager!!.getIndicator() != null) {
                if (gravity_ver != 0) {
                    ultraViewPager!!.getIndicator().setGravity(gravity_hor or gravity_ver)
                } else {
                    ultraViewPager!!.getIndicator().setGravity(gravity_hor)
                }
            }
        }
        if (parent === indicatorGravityVer) {
            when (position) {
                0 -> gravity_ver = Gravity.TOP
                1 -> gravity_ver = Gravity.BOTTOM
                2 -> gravity_ver = Gravity.CENTER_VERTICAL
            }
            if (ultraViewPager!!.getIndicator() != null) {
                if (gravity_hor != 0) {
                    ultraViewPager!!.getIndicator().setGravity(gravity_hor or gravity_ver)
                } else {
                    ultraViewPager!!.getIndicator().setGravity(gravity_ver)
                }
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (buttonView === loopCheckBox) {
            ultraViewPager!!.setInfiniteLoop(isChecked)
        }
        if (buttonView === autoScrollCheckBox) {
            if (isChecked) {
                val special = SparseIntArray()
                special.put(0, 5000)
                special.put(1, 1500)
                ultraViewPager!!.setAutoScroll(2000, special)
            } else
                ultraViewPager!!.disableAutoScroll()
        }
    }

    override fun onClick(p0: View?) {
        ultraViewPager!!.getIndicator().build()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

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
