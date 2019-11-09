package com.electropeyk.squenda.activities
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.electropeyk.squenda.fragments.MelodyIntercomFragment
import com.electropeyk.squenda.utils.NoSwipePager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.electropeyk.squenda.adpter.BottomBarAdapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.electropeyk.squenda.R
import com.electropeyk.squenda.fragments.MelodyDoor1Fragment
import com.electropeyk.squenda.fragments.MelodyDoor2Fragment
import kotlinx.android.synthetic.main.activity_melody.*


class MelodyActivity : AppCompatActivity(){
    private var viewPager: NoSwipePager? = null
    private var pagerAdapter: BottomBarAdapter? = null
    private val notificationVisible = false
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.electropeyk.squenda.R.layout.activity_melody)
        setupViewPager()
        setupBottomNavBehaviors()
        setupBottomNavStyle()
        addBottomNavigationItems()
        bottom_navigation!!.setCurrentItem(0)
        bottom_navigation.setOnTabSelectedListener(AHBottomNavigation.OnTabSelectedListener { position, wasSelected ->
            //                fragment.updateColor(ContextCompat.getColor(MainActivity.this, colors[pos]));

            if (!wasSelected)
                viewPager!!.setCurrentItem(position)

            // remove notification badge
            val lastItemPos = bottom_navigation.itemsCount - 1
            if (notificationVisible && position == lastItemPos)
                bottom_navigation.setNotification(AHNotification(), lastItemPos)

            true
        })
        Handler().postDelayed(Runnable {
            //here call the second method
            val intent = Intent(this, ScreenSaverActivity::class.java)
            // start your next activity
            startActivity(intent)
        }, 90000)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupViewPager() {
        viewPager = findViewById<View>(com.electropeyk.squenda.R.id.viewpager) as NoSwipePager
        viewPager!!.setPagingEnabled(false)
        pagerAdapter = BottomBarAdapter(supportFragmentManager)
        pagerAdapter!!.addFragments(createFragmentDoor1())
        pagerAdapter!!.addFragments(createFragmentDoo2())
        pagerAdapter!!.addFragments(createFragmentInterComm())
        viewPager!!.setAdapter(pagerAdapter)
    }

    private fun createFragmentDoor1(): MelodyDoor1Fragment {
        val fragment = MelodyDoor1Fragment()
        return fragment
    }
    private fun createFragmentDoo2(): MelodyDoor2Fragment {
        val fragment = MelodyDoor2Fragment()
        return fragment
    }
    private fun createFragmentInterComm(): MelodyIntercomFragment {
        val fragment = MelodyIntercomFragment()
        return fragment
    }


    private fun passFragmentArguments(color: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("color", color)
        return bundle
    }

    fun setupBottomNavBehaviors() {
        //        bottomNavigation.setBehaviorTranslationEnabled(false);

        /*
        Before enabling this. Change MainActivity theme to MyTheme.TranslucentNavigation in
        AndroidManifest.

        Warning: Toolbar Clipping might occur. Solve this by wrapping it in a LinearLayout with a top
        View of 24dp (status bar size) height.
         */
        bottom_navigation.setTranslucentNavigationEnabled(false)
    }

    /**
     * Adds styling properties to [AHBottomNavigation]
     */
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    private fun setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.

        Will not be visible if setColored(true) and default current item is set.
         */
        bottom_navigation.defaultBackgroundColor =R.color.colorBackGround
        bottom_navigation.accentColor = R.color.colorBackGround
        bottom_navigation.setInactiveColor(getColor(R.color.colorBackGround))

        // Colors for selected (active) and non-selected items.
        bottom_navigation.setColoredModeColors(
            getColor(R.color.colorBlue),
            getColor(R.color.colorlightGray)
        )

        //  Enables Reveal effect
        bottom_navigation.setColored(true)

        //  Displays item Title always (for selected and non-selected items)
        bottom_navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW)
    }

    /**
     * Adds (items) [AHBottomNavigationItem] to [AHBottomNavigation]
     * Also assigns a distinct color to each Bottom Navigation item, used for the color ripple.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun addBottomNavigationItems() {
        val item1 = AHBottomNavigationItem(getString(R.string.door1)  ,
            getDrawable(R.drawable.ic_windows_door),
            getColor(R.color.colorBackGround))
        val item2 = AHBottomNavigationItem(getString(R.string.door2),
            getDrawable(R.drawable.ic_windows_door),
            getColor(R.color.colorBackGround))
        val item3 = AHBottomNavigationItem(getString(R.string.inter_com),
            getDrawable(R.drawable.ic_windows_door),
            getColor(R.color.colorBackGround))

        bottom_navigation.addItem(item1)
        bottom_navigation.addItem(item2)
        bottom_navigation.addItem(item3)
    }


    /**
     * Simple facade to fetch color resource, so I avoid writing a huge line every time.
     *
     * @param color to fetch
     * @return int color value.
     */
    private fun fetchColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(this, color)
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
