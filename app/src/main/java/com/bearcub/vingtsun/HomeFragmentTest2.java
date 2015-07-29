package com.bearcub.vingtsun;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bearcub.slidingtabslibrary.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 7/22/2015.
 */
public class HomeFragmentTest2 extends android.support.v4.app.Fragment {
    private static final String TAG = HomeFragmentTest2.class.getSimpleName();
    private static final int NUM_PAGES = 3;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<HomePagerItem> mHomePagerItems = new ArrayList<HomePagerItem>();
    private SlidingTabLayout mSlidingTabLayout;

    public HomeFragmentTest2() {
        //required empty constructor
    }

    public static HomeFragmentTest2 newInstance(){
        return new HomeFragmentTest2();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTabs();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_test_2, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = (ViewPager) getActivity().findViewById(R.id.view_pager_home);
        FragmentManager fm = getChildFragmentManager();
        mPagerAdapter = new HomeFragmentTest2PagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mSlidingTabLayout = (SlidingTabLayout) getActivity().findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return mHomePagerItems.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mHomePagerItems.get(position).getDividerColor();
            }

        });
    }

    private void populateTabs(){
        mHomePagerItems.add(new HomePagerItem(
                "Techniques", // Title
                Color.BLUE, // Indicator color
                Color.GRAY // Divider color
        ));

        mHomePagerItems.add(new HomePagerItem(
                "Streak", // Title
                Color.RED, // Indicator color
                Color.GRAY // Divider color
        ));

        mHomePagerItems.add(new HomePagerItem(
                "Training Log", // Title
                Color.YELLOW, // Indicator color
                Color.GRAY // Divider color
        ));

        mHomePagerItems.add(new HomePagerItem(
                "Bonus", // Title
                Color.GREEN, // Indicator color
                Color.GRAY // Divider color
        ));
    }

    private class HomeFragmentTest2PagerAdapter extends FragmentPagerAdapter {

        public HomeFragmentTest2PagerAdapter(FragmentManager fm) {
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
            return HomeFragmentTest2Content.newInstance(mTitle, mIndicatorColor, mDividerColor);
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
