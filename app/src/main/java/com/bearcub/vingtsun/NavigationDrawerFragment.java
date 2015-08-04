package com.bearcub.vingtsun;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.bearcub.absnavigationdrawerlibrary.AbstractNavigationDrawer;
import com.bearcub.absnavigationdrawerlibrary.NavigationDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 7/16/2015.
 */
public class NavigationDrawerFragment extends AbstractNavigationDrawer {
    public static final String SHARED_PREFERENCES_FILE_NAME = "vtmaa_shared_preferences";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private static final String TAG = "nav_drawer_fragment";

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private NavigationDrawerAdapter adapter;
    private boolean userLearnedDrawer;
    private boolean fromSavedInstanceState;
    private View containerView;
    OnTouchNavigationItemSelectedListener mCallback;

    public NavigationDrawerFragment(){
    }

    @Override
    public List<NavigationDrawerItem> getItemData() {
        List<NavigationDrawerItem> list = new ArrayList<>();
        int[] images = {R.drawable.ic_check_box, R.drawable.ic_check_box, R.drawable.ic_check_box,
                R.drawable.ic_check_box, R.drawable.ic_check_box};
        String[] labels = {"Glossary", "My Training", "Forms Test 1", "Home Test 2",
                "Home Test 3"};

        /**
         for (int i = 0; i < images.length && i < labels.length; i++){
         ItemData item = new ItemData();
         item.imageId = images[i%images.length];
         item.label = labels[i%labels.length];
         list.add(item);
         } */
        for (int i = 0; i < images.length && i < labels.length; i++ ){
            NavigationDrawerItem item = new NavigationDrawerItem();
            item.imageId = images[i];
            item.label = labels[i];
            list.add(item);
        }

        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getNavigationItemViewId() {
        return R.layout.item_navigation_drawer;
    }

    @Override
    public int getRecyclerViewId() {
        return R.id.navigation_drawer_recycler_view;
    }

    @Override
    public int getMainLayoutId() {
        return R.layout.fragment_navigation_drawer;
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public int getTextViewId() {
        return R.id.navigation_row_text;
    }

    @Override
    public int getImgViewId() {
        return R.id.navigation_row_img;
    }
}
