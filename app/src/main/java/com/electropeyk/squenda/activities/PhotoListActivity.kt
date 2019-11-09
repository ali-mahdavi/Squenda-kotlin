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
import com.electropeyk.squenda.R
import com.electropeyk.squenda.adpter.PhotoRecyclerViewAdapter
import com.electropeyk.squenda.models.MetaFile
import com.electropeyk.squenda.utils.Common
import com.electropeyk.squenda.utils.Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST
import com.electropeyk.squenda.utils.GridDividerItemDecoration
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_photo_list.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class PhotoListActivity : AppCompatActivity(), PhotoRecyclerViewAdapter.ItemClickListener,
    PhotoRecyclerViewAdapter.ItemLongClickListener {
    private val NUM_COLUMNS = 6
    private val TAG = PhotoListActivity::class.java.simpleName
    private var mDividerItemDecoration: RecyclerView.ItemDecoration? = null
    lateinit var adapter: PhotoRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    private var isOpen: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        super.onCreate(savedInstanceState)
        setContentView(com.electropeyk.squenda.R.layout.activity_photo_list)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        Paper.init(this);
        fr_msg.setVisibility(View.GONE)
        recyclerView = findViewById(com.electropeyk.squenda.R.id.recycle_photos)
        ABSOLUTE_PATH_NAMES_PHOTO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_PHOTO)
        initRecyclerView();
        btn_home_photo_list.setOnClickListener {
            val intent = Intent(this, MyHomeActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        btn_setting_photo_list.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            startActivity(intent)
            finish()
        }

        btn_profile_photo_list.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        btn_share.setVisibility(View.INVISIBLE);
        btn_trash.setVisibility(View.INVISIBLE);
        btn_trash.setOnClickListener {
            var checked: List<MetaFile> = Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.filter { it.checked == true }

            if (checked.size > 0) {
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
        Handler().postDelayed(Runnable {
            //here call the second method
            val intent = Intent(this, ScreenSaverActivity::class.java)
            // start your next activity
            startActivity(intent)
        }, 90000)

        btn_share.setOnClickListener {
            var checked: List<MetaFile> = Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.filter { it.checked == true }

            if (checked.size > 0) {
                val imageUris = ArrayList<Uri>()
                for (pos in checked) {
                    imageUris.add(Uri.parse(pos.path))
                }
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND_MULTIPLE
                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
                shareIntent.type = "image/*"
                startActivity(Intent.createChooser(shareIntent, "Share images to.."))

            } else
                Toast.makeText(this, "There is not any item selected for share", Toast.LENGTH_LONG).show()

        }


        btn_check_photo.setOnClickListener {
            deletePhotos()
            val RightSwipe = AnimationUtils.loadAnimation(this, com.electropeyk.squenda.R.anim.left_swipe)
            fr_msg.startAnimation(RightSwipe)
            fr_msg.setVisibility(View.GONE)
            isOpen = false

        }

        img_back_photo_list.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            // start your next activity
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        txt_time_photo_list.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
        val thread = object : Thread() {

            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            txt_time_photo_list.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
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
        txt_date_photo_list.text = "$day,$month $dayOfMonth"


    }


    override fun onItemClick(view: View, position: Int) {
        val mediaIntent = Intent(this, PhotoPreviewActivity::class.java)
        mediaIntent.putExtra("pos", position)
        startActivity(mediaIntent)
    }

    override fun onItemLongClick(view: View, position: Int) {
        btn_trash.setVisibility(View.INVISIBLE)
        btn_share.setVisibility(View.INVISIBLE)
        for (MetaFile in ABSOLUTE_PATH_NAMES_PHOTO_LIST) {
            if (MetaFile.checked) {
                btn_trash.setVisibility(View.VISIBLE)
                btn_share.setVisibility(View.VISIBLE)
            }
        }

    }

    private fun deletePhotos() {
        var checked: List<MetaFile> = Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.filter { it.checked == true }
        var isReset=false
        if(Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.size==checked.size)
            isReset=true
        if(checked.size==0)return
        for (MetaFile in checked) {

                val fdelete = File(MetaFile.path)
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.remove(MetaFile)
                    }
                }

        }
        if(isReset)
            Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST =ArrayList<MetaFile>()
        adapter = PhotoRecyclerViewAdapter(this, Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST)
        adapter!!.setClickListener(this)
        adapter!!.setLongClickListener(this)
        recyclerView!!.adapter = adapter
        Paper.book(Common.DATABASE).write(Common.ABSOLUTE_PATH_NAMES_PHOTO, Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST)



    }


    private fun initRecyclerView() {
        // set up the RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, NUM_COLUMNS)
        val dividerDrawable = ContextCompat.getDrawable(this, com.electropeyk.squenda.R.drawable.divider_sample)
        mDividerItemDecoration = GridDividerItemDecoration(dividerDrawable!!, dividerDrawable, NUM_COLUMNS)
        recyclerView?.addItemDecoration(mDividerItemDecoration as GridDividerItemDecoration)

        adapter = PhotoRecyclerViewAdapter(this, ABSOLUTE_PATH_NAMES_PHOTO_LIST)
        adapter.setClickListener(this)
        adapter.setLongClickListener(this)
        recyclerView.adapter = adapter
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
