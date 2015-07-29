package com.bearcub.vingtsun;

/**
 * Created by Home on 7/16/2015.
 */
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.bearcub.recyclerviewlibrary.BaseRecyclerView;


public class MainActivity extends AppCompatActivity implements TrainingFragment.OnTouchItemSelectedListener,
        NavigationDrawerFragment.OnTouchNavigationItemSelectedListener, BaseRecyclerView.OnTouchItemSelectedListener{
    private static final String TAG = "main_activity";
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private CardView mToolbarCardView;
    private boolean mToolbarElevationOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpNavigationDrawer();

        if (savedInstanceState == null) {
            android.app.Fragment fragment = getHomeFragment(1);
            setMainFragment(fragment, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUpNavigationDrawer(){
        FragmentManager fm = getFragmentManager();
        int fragmentId = R.id.nav_drawer_fragment;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment) fm.findFragmentById(R.id.nav_drawer_fragment);
        navigationDrawerFragment.initDrawer(fragmentId, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    public void setUpToolbar(){
        mToolbarElevationOn = true;
        mToolbarCardView = (CardView)findViewById(R.id.toolbar_card_view);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //adds or replaces fragment in main content view
    public void setMainFragment(android.app.Fragment fragment, boolean fromSavedInstance){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);

        if(!fromSavedInstance){
            transaction.add(R.id.main_content_view, fragment);
        } else {
            transaction.replace(R.id.main_content_view, fragment);
        }
        transaction.commit();
    }

    public void setMainFragment(Fragment fragment, boolean fromSavedInstance){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.replace(R.id.main_content_view, fragment).commit();

        /**
        if(!fromSavedInstance){
            transaction.add(R.id.main_content_view, fragment);
        } else {
            transaction.replace(R.id.main_content_view, fragment);
        }
        transaction.commit();
         */
    }

    public TrainingFragment getTrainingFragment(int position){
        String tag = generateTag("training", position);
        TrainingFragment fragment = (TrainingFragment)getFragmentManager().findFragmentByTag(tag);
        if(fragment==null){
            fragment = TrainingFragment.newInstance();
        }
        return fragment;
    }

    public DefinitionsFragment getDefinitionsFragment(int position){
        String tag = generateTag("definitions", position);
        DefinitionsFragment fragment = (DefinitionsFragment)getFragmentManager().findFragmentByTag(tag);
        if(fragment == null){
            fragment = DefinitionsFragment.newInstance();
        }
        return fragment;
    }

    public FormsFragmentTest3 getFormsFragmentTest3(int position){
        String tag = generateTag(FormsFragmentTest3.class.getSimpleName(), position);
        FormsFragmentTest3 fragment = (FormsFragmentTest3)getFragmentManager().findFragmentByTag(tag);
        if(fragment==null){
            fragment = FormsFragmentTest3.newInstance();
        }
        return fragment;
    }

    public HomeFragment getHomeFragment(int position){
        String tag = generateTag(HomeFragment.class.getSimpleName(), position);
        HomeFragment fragment = (HomeFragment)getFragmentManager().findFragmentByTag(tag);
        if(fragment==null){
            fragment = HomeFragment.newInstance();
        }
        return fragment;
    }

    public HomeFragmentTest2 getHomeTest2Fragment(int position){
        String tag = generateTag(HomeFragmentTest2.class.getSimpleName(), position);
        HomeFragmentTest2 fragment = (HomeFragmentTest2)getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment==null){
            fragment = HomeFragmentTest2.newInstance();
        }
        return fragment;
    }

    public HomeFragmentTest3 getHomeTest3Fragment(int position){
        String tag = generateTag(HomeFragmentTest3.class.getSimpleName(), position);
        HomeFragmentTest3 fragment = (HomeFragmentTest3)getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment==null){
            fragment = HomeFragmentTest3.newInstance();
        }
        return fragment;
    }

    //generates tag for fragment
    public String generateTag(String name, int position){
        return name + position;
    }

    @Override
    public void onTouchItemSelected(View view, int position) {
        Toast.makeText(this, "onTouchItemSelected at " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTouchNavigationItemSelected(View view, int position) {
        android.app.Fragment fragment;
        switch(position){
            case 0:
                if(!mToolbarElevationOn){
                    toggleToolbarElevation();
                }
                fragment = getDefinitionsFragment(position);
                break;
            case 1:
                if(!mToolbarElevationOn){
                    toggleToolbarElevation();
                }
                fragment = getTrainingFragment(position);
                break;
            case 2:
                if(!mToolbarElevationOn){
                    toggleToolbarElevation();
                }
                fragment = getFormsFragmentTest3(position);
                break;
            case 3:
                if(!mToolbarElevationOn){
                    toggleToolbarElevation();
                }
                fragment = getHomeFragment(position);
                break;
            case 4:
                if(mToolbarElevationOn){
                    toggleToolbarElevation();
                }
                setMainFragment(getHomeTest2Fragment(position), true);
                mDrawerLayout.closeDrawers();
                return;
            case 5:
                if(mToolbarElevationOn){
                    toggleToolbarElevation();
                }
                setMainFragment(getHomeTest3Fragment(position), true);
                mDrawerLayout.closeDrawers();
                return;
            default:
                throw new IllegalArgumentException("unknown position");
        }
        setMainFragment(fragment, true);

        mDrawerLayout.closeDrawers();
    }

    public void toggleToolbarElevation(){
        float elevation;

        if(mToolbarElevationOn){
            elevation = 0;
            mToolbarElevationOn = false;
        }else{
            elevation = 5;
            mToolbarElevationOn = true;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            toolbar.setElevation(elevation);
        }else{
            mToolbarCardView.setCardElevation(elevation);
        }
    }
}