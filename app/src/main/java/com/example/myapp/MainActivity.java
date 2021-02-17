package com.example.myapp;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();



        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UniHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        if(!haveNetwork(this)){
            connectionDialog1();
        }
    }
    public void connectionDialog1(){
        myDialog = new Dialog(MainActivity.this);
        myDialog.setContentView(R.layout.customconnectiondialog);
        myDialog.setTitle("Error");
        myDialog.show();
    }

    private boolean haveNetwork(Context context){
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        // NetworkInfo [] networkInfos = connectivityManager.getAllNetworkInfo();
        if (connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

    @Override
    public void onBackPressed() {


        List fragmentList = getSupportFragmentManager().getFragments();

        boolean handled = false;
        for(Object f : fragmentList) {
            if(f instanceof LibraryFragment) {
                handled = ((LibraryFragment)f).onBackPressed();

                if(handled) {
                    break;
                }
            }

            if(f instanceof LMSFragment) {
                handled = ((LMSFragment)f).onBackPressed();

                if(handled) {
                    break;
                }
            }

            if(f instanceof PESFragment) {
                handled = ((PESFragment)f).onBackPressed();

                if(handled) {
                    break;
                }
            }
        }
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(!handled) {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_library:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LibraryFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UniHomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_staff:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StaffFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_fhss:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FHSSFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_lms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LMSFragment()).addToBackStack(null).commit();

                break;
            case R.id.nav_pes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PESFragment()).addToBackStack(null).commit();

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}