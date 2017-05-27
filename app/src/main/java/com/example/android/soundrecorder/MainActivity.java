package com.example.android.soundrecorder;

import android.support.design.widget.TabLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
