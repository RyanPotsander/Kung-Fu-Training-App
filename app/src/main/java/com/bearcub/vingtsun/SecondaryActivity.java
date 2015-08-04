package com.bearcub.vingtsun;

/**
 * Created by Home on 7/16/2015.
 */
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
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


public class SecondaryActivity extends BaseActivity implements TrainingFragment.OnTouchItemSelectedListener,
        BaseRecyclerView.OnTouchItemSelectedListener{
    private static final String TAG = SecondaryActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private boolean mToolbarElevationOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        mToolbar = setUpToolbar(R.id.my_toolbar);
        CardView toolbarCardWrapper = (CardView)findViewById(R.id.toolbar_card_view);
        toggleToolbarElevation(mToolbar, toolbarCardWrapper, false); //turn it on
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_secondary);
        setUpDrawer(mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);

        setNavigationItemListener();

        if (savedInstanceState == null) {
            setMainFragment(getFormsFragmentTest3(1), false);
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

    private void setNavigationItemListener(){
        NavigationView mNavigationView = (NavigationView)findViewById(R.id.navigation_view_secondary);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_glossary:
                        setMainFragment(getDefinitionsFragment(1), false);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.navigation_item_training:
                        i.putExtra("position", 2);
                        startActivity(i);
                    case R.id.navigation_item_forms:
                        setMainFragment(getFormsFragmentTest3(1), false);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.navigation_item_home:
                        i.putExtra("position", 0);
                        startActivity(i);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    //adds or replaces fragment in main content view
    public void setMainFragment(android.app.Fragment fragment, boolean fromSavedInstance){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.replace(R.id.secondary_content_view, fragment);
        transaction.commit();
    }

    public DefinitionsFragment getDefinitionsFragment(int position){
        String tag = generateTag("definitions", position);
        DefinitionsFragment fragment = (DefinitionsFragment)getFragmentManager().findFragmentByTag(tag);
        if(fragment == null){
            fragment = DefinitionsFragment.newInstance();
        }
        return fragment;
    }

    public FormsFragment getFormsFragmentTest3(int position){
        String tag = generateTag(FormsFragment.class.getSimpleName(), position);
        FormsFragment fragment = (FormsFragment)getFragmentManager().findFragmentByTag(tag);
        if(fragment==null){
            fragment = FormsFragment.newInstance();
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
}