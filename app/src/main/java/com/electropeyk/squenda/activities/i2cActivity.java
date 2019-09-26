package com.electropeyk.squenda.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.electropeyk.squenda.R;
import com.electropeyk.squenda.fragments.GPIOFragment;
import com.electropeyk.squenda.fragments.I2CFragment;
import com.electropeyk.squenda.jni.I2C;

import java.util.Locale;

public class i2cActivity extends AppCompatActivity {


    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i2c);

        final ProgressDialog gain_permission = ProgressDialog.show(
                this, "Please wait", "Gaining permissions...",
                true);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //  UART.getPermission();
                    I2C.getPermission();
                    //  UART.fd = UART.serialOpen("/dev/ttyS7", 115200);
                    Thread.sleep(2000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                gain_permission.dismiss();

            }
        }).start();


        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        SectionsPagerAdapter adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position) {
                //  case 0:
                //        fragment = new UARTFragment();
                //   break;
                case 0:
                    fragment = new I2CFragment();
                    break;
                case 1:
                    fragment = new GPIOFragment();
                    break;
            }
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
               /* case 0:
                    return getString(R.string.title_section1).toUpperCase(l);*/
                case 0:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
}
