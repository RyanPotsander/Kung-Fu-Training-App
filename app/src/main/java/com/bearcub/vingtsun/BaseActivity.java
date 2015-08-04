package com.bearcub.vingtsun;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Home on 7/31/2015.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public Toolbar setUpToolbar(int toolbarId){
        Toolbar toolbar = (Toolbar)findViewById(toolbarId);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    public void setUpDrawer(DrawerLayout drawerLayout, Toolbar toolbar, int openId, int closedId){
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, openId, closedId){
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void toggleToolbarElevation(Toolbar toolbar, CardView cardView, boolean currentState){
        float elevation;

        if(currentState){
            elevation = 0;
        }else{
            elevation = 5;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            toolbar.setElevation(elevation);
        }else{
            cardView.setCardElevation(elevation);
        }
    }
}
