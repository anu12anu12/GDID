package com.gdid.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gdid.com.gdid.utils.GDIDConstants;
import com.gdid.fragments.BugListFragment;
import com.gdid.fragments.DashboardFragment;
import com.gdid.fragments.OrderDetailsFragment;
import com.gdid.fragments.OrderListFragment;
import com.gdid.fragments.ScanBarcodeListFragment;
import com.gdid.material_management.R;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long aId) {
        selectItem(position);
    }

    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;


    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);


        mTitle = mDrawerTitle = getTitle();
        mMenuTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(this);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                true,
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(GDIDConstants.DASHBOARD_POSITION);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        getActionBar().setTitle(mTitle);
        getSupportActionBar().setTitle(mTitle);
    }

    private void selectItem(int position) {
        Fragment fragment = null;
        // update the main content by replacing fragments
        switch (position) {
            case GDIDConstants.DASHBOARD_POSITION:
                fragment = new DashboardFragment();
                break;
            case GDIDConstants.BUGLIST_POSITION:
                fragment = new BugListFragment();
                break;
            case GDIDConstants.ORDERLIST_POSITION:
                fragment = new OrderListFragment();
                break;
            case GDIDConstants.SCANBARCODE_POSITION:
                fragment = new ScanBarcodeListFragment();
                break;
            case GDIDConstants.ORDERDETAILS_POSITION:
                fragment = new OrderDetailsFragment();
                break;

            default:
                fragment = new DashboardFragment();
                break;

        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.mainmenu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    /* Called whenever we call invalidateOptionsMenu() */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_logout).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
//        switch(item.getItemId()) {
//            case R.id.action_logout:
//                // create intent to perform Log out
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);
    }
}
