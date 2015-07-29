package com.bearcub.vingtsun;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bearcub.ioslidingtabslibrary.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 7/22/2015.
 * same as homefragmenttest2 but using tabs from io app rather than tab sample
 */
public class HomeFragmentTest3 extends Fragment{
    private static final int NUM_PAGES = 3;
    private ViewPager mViewPager;
    private HomeFragmentTest3PagerAdapter mPagerAdapter;
    private List<HomePagerItem3> mHomePagerItems = new ArrayList<HomePagerItem3>();
    private SlidingTabLayout mSlidingTabLayout;

    public HomeFragmentTest3() {
        //required empty constructor
    }

    public static HomeFragmentTest3 newInstance(){
        return new HomeFragmentTest3();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTabs();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_test_3, container, false);

        mViewPager = (ViewPager)view.findViewById(R.id.view_pager_home_test_3);
        mSlidingTabLayout = (SlidingTabLayout)view.findViewById(R.id.sliding_tabs_test_3);
        mSlidingTabLayout.setDistributeEvenly(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        FragmentManager fm = getChildFragmentManager();
        mPagerAdapter = new HomeFragmentTest3PagerAdapter(fm);
        mViewPager.setAdapter(mPagerAdapter); //

        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return mHomePagerItems.get(position).getIndicatorColor();
            }
        });
    }

    private void populateTabs(){
        mHomePagerItems.add(new HomePagerItem3(
                "Training", // Title
                Color.BLUE, // Indicator color
                Color.GRAY // Divider color
        ));

        mHomePagerItems.add(new HomePagerItem3(
                "Social", // Title
                Color.RED, // Indicator color
                Color.GRAY // Divider color
        ));

        mHomePagerItems.add(new HomePagerItem3(
                "Log", // Title
                Color.YELLOW, // Indicator color
                Color.GRAY // Divider color
        ));
    }

    private class HomeFragmentTest3PagerAdapter extends FragmentPagerAdapter {

        public HomeFragmentTest3PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mHomePagerItems.get(position).createFragment();
        }

        @Override
        public int getCount() {
            return mHomePagerItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mHomePagerItems.get(position).getTitle();
        }
    }

    static class HomePagerItem3 {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;

        HomePagerItem3(CharSequence title, int indicatorColor, int dividerColor) {
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }

        android.support.v4.app.Fragment createFragment() {
            return HomeFragmentTest3Content.newInstance(mTitle, mIndicatorColor, mDividerColor);
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
    }
}
