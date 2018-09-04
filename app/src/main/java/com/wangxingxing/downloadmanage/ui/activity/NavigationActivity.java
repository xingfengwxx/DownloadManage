package com.wangxingxing.downloadmanage.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.orhanobut.logger.Logger;
import com.wangxingxing.downloadmanage.R;
import com.wangxingxing.downloadmanage.ui.fragment.CompleteFragment;
import com.wangxingxing.downloadmanage.ui.fragment.DownloadFragment;
import com.wangxingxing.downloadmanage.ui.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity implements CompleteFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"下载中", "已下载", "设置"};
    private MyPagerAdapter mMyPagerAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initViewPager();
    }

    private void initViewPager() {
        mFragments.add(DownloadFragment.getInstance());
        mFragments.add(CompleteFragment.getInstance());
        mFragments.add(SettingFragment.getInstance());
        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mMyPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Logger.i("onPageScrolled: " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Logger.i("onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Logger.i("onPageScrollStateChanged: " + state);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Logger.i("onFragmentInteraction: " + uri.toString());
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
