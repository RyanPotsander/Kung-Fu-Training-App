package com.bearcub.vingtsun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 7/31/2015.
 */
public class MainActivity extends BaseActivity implements TrainingFragment.OnTouchItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NUM_PAGES = 3;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private List<HomePagerItem> mPagerItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateTabs();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_main);
        mViewPager = (ViewPager)findViewById(R.id.view_pager_main);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        TabLayout mTabLayout = (TabLayout)findViewById(R.id.tab_layout_main);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mToolbar = setUpToolbar(R.id.my_toolbar);
        CardView toolbarCardWrapper = (CardView)findViewById(R.id.toolbar_card_view);
        toggleToolbarElevation(mToolbar, toolbarCardWrapper, true); // turn it off

        setUpDrawer(mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);

        setNavigationItemListener();

        Intent intent = getIntent();
        int viewPagerPosition = intent.getIntExtra("position", 0);
        selectViewPagerPosition(viewPagerPosition);
    }

    private void populateTabs(){
        mPagerItems.add(new HomePagerItem(
                "Techniques", // Title
                Color.BLUE, // Indicator color
                Color.GRAY // Divider color
        ));

        mPagerItems.add(new HomePagerItem(
                "Social", // Title
                Color.RED, // Indicator color
                Color.GRAY // Divider color
        ));

        mPagerItems.add(new HomePagerItem(
                "Training Log", // Title
                Color.YELLOW, // Indicator color
                Color.GRAY // Divider color
        ));

        mPagerItems.add(new HomePagerItem(
                "Bonus", // Title
                Color.GREEN, // Indicator color
                Color.GRAY // Divider color
        ));
    }

    private void selectViewPagerPosition(int position){
        mViewPager.setCurrentItem(position);
    }

    private void setNavigationItemListener(){
        NavigationView mNavigationView = (NavigationView)findViewById(R.id.navigation_view_main);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent i = new Intent(getApplicationContext(), SecondaryActivity.class);
                switch (id) {
                    case R.id.navigation_item_glossary:
                        startActivity(i);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.navigation_item_training:
                        selectViewPagerPosition(2);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.navigation_item_forms:
                        startActivity(i);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.navigation_item_home:
                        selectViewPagerPosition(0);
                        mDrawerLayout.closeDrawers();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onTouchItemSelected(View view, int position) {
        Snackbar.make(view, "clicked " + position, Snackbar.LENGTH_SHORT);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    return HomePagerTechniques.newInstance();
                case 1:
                    return SocialFragment.newInstance();
                case 2:
                    return TrainingFragment.newInstance();
                default:
                    throw new IllegalArgumentException("unknown position");
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPagerItems.get(position).getTitle();
        }
    }

    static class HomePagerItem {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;

        HomePagerItem(CharSequence title, int indicatorColor, int dividerColor) {
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }

        android.support.v4.app.Fragment createFragment() {
            return HomePagerTechniques.newInstance();
        }

        /**
         * the title which represents this tab. In this sample this is used directly by
         *  android.support.v4.view.Pa)}
         */
        CharSequence getTitle() {
            return mTitle;
        }

        /**
         * @return the color to be used for indicator on the SlidingTabLayout
         */
        int getIndicatorColor() {
            return mIndicatorColor;
        }

        /**
         * @return the color to be used for right divider on the SlidingTabLayout
         */
        int getDividerColor() {
            return mDividerColor;
        }
    }

}
