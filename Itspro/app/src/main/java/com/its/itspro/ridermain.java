package com.its.itspro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class ridermain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ridermain);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new usermap()).commit();
            setTitle("Booking");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (toggle.onOptionsItemSelected(menuItem)){
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Log.i("here","Entered Selected Navigation Syatem");
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id){
            case(R.id.booking):
                fragment = new usermap();
                Log.i("here","Booking Fragment Initialized");
                break;
            case (R.id.track):
                fragment = new track();
                Log.i("here","Track yourself Fragment Initialized");
                break;
            case R.id.history:
                fragment = new history();
                Log.i("here","History Fragment Initialized");
                break;
            case R.id.feedback:
                fragment = new feedback();
                Log.i("here","Feedback Fragment Initialized");
                break;
            case R.id.about:
                fragment = new about();
                Log.i("here","About Fragment Initialized");
                break;
        }
        if(fragment!= null){

            FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,fragment);
            transaction.commit();
            Log.i("fragment transaction","complete");
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
