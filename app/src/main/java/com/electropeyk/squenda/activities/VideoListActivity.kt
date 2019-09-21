package com.electropeyk.squenda.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electropeyk.squenda.adpter.VideoRecyclerViewAdapter
import com.electropeyk.squenda.utils.Common
import com.electropeyk.squenda.utils.Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST
import com.electropeyk.squenda.utils.Common.VIDEO_NUM_SELECCTED
import com.electropeyk.squenda.utils.GridDividerItemDecoration
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_video_list.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class VideoListActivity : AppCompatActivity(), VideoRecyclerViewAdapter.ItemClickListener,
    VideoRecyclerViewAdapter.ItemLongClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: VideoRecyclerViewAdapter
    private val NUM_COLUMNS = 6
    private var isOpen: Boolean = false

    private var mDividerItemDecoration: RecyclerView.ItemDecoration? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        super.onCreate(savedInstanceState)
        setContentView(com.electropeyk.squenda.R.layout.activity_video_list)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        Paper.init(this)
        fr_msg.setVisibility(View.GONE)
        recyclerView = findViewById(com.electropeyk.squenda.R.id.recycle_videos)
        ABSOLUTE_PATH_NAMES_VIDEO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_VIDEO)
        initRecyclerView()
        btn_share.setVisibility(View.INVISIBLE)
        btn_trash.setVisibility(View.INVISIBLE)

        btn_home_video_list.setOnClickListener {
            val intent = Intent(this, MyHomeActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }
        btn_setting_video_list.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            startActivity(intent)
            finish()
        }

        btn_profile_video_list.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }

        btn_trash.setOnClickListener {
            if (VIDEO_NUM_SELECCTED.size > 0) {
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


            } else {
                Toast.makeText(this, "There is not any item selected for delete", Toast.LENGTH_LONG).show()
            }


        }

        btn_check.setOnClickListener {
            deleteVideos()
            val RightSwipe = AnimationUtils.loadAnimation(this, com.electropeyk.squenda.R.anim.left_swipe)
            fr_msg.startAnimation(RightSwipe)
            fr_msg.setVisibility(View.GONE)
            isOpen=false

        }

        btn_share.setOnClickListener {
            if (VIDEO_NUM_SELECCTED.size > 0) {
                val imageUris = ArrayList<Uri>()
                for (pos in VIDEO_NUM_SELECCTED) {
                    imageUris.add(Uri.parse(Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST[pos]))
                }
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND_MULTIPLE
                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
                shareIntent.type = "image/*"
                startActivity(Intent.createChooser(shareIntent, "Share images to.."))

            } else
                Toast.makeText(this, "There is not any item selected for share", Toast.LENGTH_LONG).show()

        }

        img_back_video_list.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
            finish()
        }
        txt_time_video_list.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())

        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_video_list.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
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
        txt_date_video_list.text = "$day,$month $dayOfMonth"


    }

    private fun deleteVideos() {
        if(ABSOLUTE_PATH_NAMES_VIDEO_LIST.size==VIDEO_NUM_SELECCTED.size)
            ABSOLUTE_PATH_NAMES_VIDEO_LIST=ArrayList()
        else {
            for (position in VIDEO_NUM_SELECCTED) {

                val fdelete = File(ABSOLUTE_PATH_NAMES_VIDEO_LIST[position])
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        ABSOLUTE_PATH_NAMES_VIDEO_LIST.removeAt(position)
                    }
                }


            }
        }
        adapter = VideoRecyclerViewAdapter(this, ABSOLUTE_PATH_NAMES_VIDEO_LIST)
        recyclerView!!.adapter = adapter
        Paper.book(Common.DATABASE).write(Common.ABSOLUTE_PATH_NAMES_VIDEO, Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST)
    }

    private fun initRecyclerView() {
        // set up the RecyclerView
        recyclerView?.setLayoutManager(GridLayoutManager(this, NUM_COLUMNS))
        val dividerDrawable = ContextCompat.getDrawable(this, com.electropeyk.squenda.R.drawable.divider_sample)
        mDividerItemDecoration = GridDividerItemDecoration(dividerDrawable!!, dividerDrawable, NUM_COLUMNS)
        recyclerView?.addItemDecoration(mDividerItemDecoration as GridDividerItemDecoration)
        adapter = VideoRecyclerViewAdapter(this, ABSOLUTE_PATH_NAMES_VIDEO_LIST)
        adapter!!.setClickListener(this)
        adapter!!.setLongClickListener(this)
        recyclerView!!.adapter = adapter
    }

    override fun onItemClick(view: View, position: Int) {
        val mediaIntent = Intent(this, VideoPreviewActivity::class.java)
        mediaIntent.putExtra("position", position)
        startActivity(mediaIntent)
    }

    override fun onItemLongClick(view: View, position: Int) {
        if (VIDEO_NUM_SELECCTED.size > 0) {
            btn_trash.visibility = View.VISIBLE
            btn_share.visibility = View.VISIBLE
        } else {
            btn_trash.visibility = View.INVISIBLE
            btn_share.visibility = View.INVISIBLE
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
