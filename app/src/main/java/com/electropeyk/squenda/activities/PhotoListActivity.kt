package com.electropeyk.squenda.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crowdfire.cfalertdialog.CFAlertDialog
import com.electropeyk.squenda.R
import com.electropeyk.squenda.adpter.PhotoRecyclerViewAdapter
import com.electropeyk.squenda.utils.Common
import com.electropeyk.squenda.utils.Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST
import com.electropeyk.squenda.utils.Common.PHOTO_NUM_SELECCTED
import com.electropeyk.squenda.utils.GridDividerItemDecoration
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_all_devices.*
import kotlinx.android.synthetic.main.activity_living_room.*
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
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        super.onCreate(savedInstanceState)
        setContentView(com.electropeyk.squenda.R.layout.activity_photo_list)
        overridePendingTransition(com.electropeyk.squenda.R.anim.fade_in, com.electropeyk.squenda.R.anim.fade_out)
        Paper.init(this);
        recyclerView = findViewById(com.electropeyk.squenda.R.id.recycle_videos)
        Common.ABSOLUTE_PATH_NAMES_VIDEO_LIST = Paper.book(Common.DATABASE).read(Common.ABSOLUTE_PATH_NAMES_VIDEO);
        initRecyclerView();
        btn_share.setVisibility(View.INVISIBLE);
        btn_trash.setVisibility(View.INVISIBLE);
        btn_trash.setOnClickListener {
            if (PHOTO_NUM_SELECCTED.size > 0) {

                val builder = CFAlertDialog.Builder(this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                    .setTitle("Delete Videos")
                    .setMessage("Are you sure to delete selected videos?")
                    .addButton(
                        "Yes", -1, -1,
                        CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                    ) { dialog, which ->
                        deletePhotos()
                        dialog.dismiss()
                    }.addButton(
                        "NO", -1, -1,
                        CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                    ) { dialog, which -> dialog.dismiss() }

                // Show the alert
                builder.show()
            } else {
                Toast.makeText(this, "There is not any item selected for delete", Toast.LENGTH_LONG).show()
            }
        }
        btn_share.setOnClickListener {
            if (PHOTO_NUM_SELECCTED.size > 0) {

                val builder = CFAlertDialog.Builder(this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                    .setTitle("Delete Videos")
                    .setMessage("Are you sure to delete selected videos?")
                    .addButton(
                        "Yes", -1, -1,
                        CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                    ) { dialog, which ->
                        deletePhotos()
                        dialog.dismiss()
                    }.addButton(
                        "NO", -1, -1,
                        CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                    ) { dialog, which -> dialog.dismiss() }

                // Show the alert
                builder.show()
            } else {
                Toast.makeText(this, "There is not any item selected for delete", Toast.LENGTH_LONG).show()
            }
        }

        img_back_photo_list.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
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
                            txt_time_photo_list.text= SimpleDateFormat("HH:mm", Locale.US).format( Date())
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
        txt_date_photo_list.text= "$day,$month $lastTwoDigits"


    }


    override fun onItemClick(view: View, position: Int) {
        val mediaIntent = Intent(this, PhotoPreviewActivity::class.java)
        mediaIntent.putExtra("url", adapter.getItem(position))
        startActivity(mediaIntent)
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.i("NUM_SELECCTED COUNT IS:", PHOTO_NUM_SELECCTED.size.toString())
        if (PHOTO_NUM_SELECCTED.size > 0) {
            btn_trash.setVisibility(View.VISIBLE)
            btn_share.setVisibility(View.VISIBLE)
        } else {
            btn_trash.setVisibility(View.INVISIBLE)
            btn_share.setVisibility(View.INVISIBLE)
        }
    }

    private fun deletePhotos() {
        for (position in PHOTO_NUM_SELECCTED) {
            val fdelete = File(ABSOLUTE_PATH_NAMES_PHOTO_LIST[position])
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    ABSOLUTE_PATH_NAMES_PHOTO_LIST.removeAt(position)
                }
            }

        }
        adapter = PhotoRecyclerViewAdapter(this, ABSOLUTE_PATH_NAMES_PHOTO_LIST)
        recyclerView.adapter?.notifyDataSetChanged()
        Paper.book(Common.DATABASE).write(Common.ABSOLUTE_PATH_NAMES_PHOTO, Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST)
    }

    private fun initRecyclerView() {
        // set up the RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, NUM_COLUMNS)
        val dividerDrawable = ContextCompat.getDrawable(this, com.electropeyk.squenda.R.drawable.divider_sample)
        mDividerItemDecoration = GridDividerItemDecoration(dividerDrawable!!, dividerDrawable, NUM_COLUMNS)
        recyclerView?.addItemDecoration(mDividerItemDecoration as GridDividerItemDecoration)
        Log.i(TAG, ABSOLUTE_PATH_NAMES_PHOTO_LIST[0])
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
