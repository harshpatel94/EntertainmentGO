package com.example.hunter.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new MainFragment()).commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        SortedFragment sf = new SortedFragment();
        onBackPressed();

        switch(id){
            case R.id.nav_MyFav:
                WishList wl = new WishList();
                setFragment(wl);
                return true;
            case R.id.nav_watchlist:

                return true;
            case R.id.nav_toprate:
                TopRated tr = new TopRated();
                setFragment(tr);
                return true;
            case R.id.nav_imdbRating:
                bundle.putString("URL", "http://10.0.2.2:8080/titles/sort/imdbrating");
                // set Fragmentclass Arguments
                sf.setArguments(bundle);
                setFragment(sf);
                return true;
            case R.id.nav_year:
                bundle.putString("URL", "http://10.0.2.2:8080/titles/sort/year");
                // set Fragmentclass Arguments
                sf.setArguments(bundle);
                setFragment(sf);
                return true;
            case R.id.nav_imdbVotes:
                bundle.putString("URL", "http://10.0.2.2:8080/titles/sort/imdbvote");
                // set Fragmentclass Arguments
                sf.setArguments(bundle);
                setFragment(sf);
                return true;
            case R.id.nav_setting:
                return true;
            case R.id.nav_logout:
                getSharedPreferences("fbPrefs", MODE_PRIVATE).edit().clear().apply();
                getSharedPreferences("MyPrefs",MODE_PRIVATE).edit().clear().apply();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                MainFragment mf = new MainFragment();
                setFragment(mf);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
