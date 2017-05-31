package com.example.android.soundrecorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.android.soundrecorder.fragments.FileViewerFragment;
import com.example.android.soundrecorder.fragments.RecordFragment;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //申请权限
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }


        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager_main);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout_main);

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case REQUEST_RECORD_AUDIO_PERMISSION:
                if (grantResults.length > 0) {

                    for (int result : grantResults) {

                        if (result != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                    }
                }else {
                    finish();
                }
                break;
            default:

        }
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        private String[] titles = { getString(R.string.tab_title_record),
                getString(R.string.tab_title_saved_recordings) };


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return RecordFragment.newInstance(position);
                case 1:
                    return FileViewerFragment.newInstance(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }





}
