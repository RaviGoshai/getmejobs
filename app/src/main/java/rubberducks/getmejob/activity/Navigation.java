package rubberducks.getmejob.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import rubberducks.getmejob.JobSearch.JobSearchActivity;
import rubberducks.getmejob.JobSearch.JobStatusActivity;
import rubberducks.getmejob.Profile.EmployeProfile;
import rubberducks.getmejob.R;
import rubberducks.getmejob.Registration.LoginSelection;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout=findViewById(R.id.navigation_framlayout);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle job_serch view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_jobSearch) {
            Intent intent=new Intent(Navigation.this, JobSearchActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_notification) {
            Intent intent=new Intent(Navigation.this, Notifcation.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_jobStatus) {
            Intent intent=new Intent(Navigation.this, JobStatusActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_profile) {
            Intent intent=new Intent(Navigation.this, EmployeProfile.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_studentHistory) {
            Intent intent=new Intent(Navigation.this, StudentHistory.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_setting) {
            Intent intent=new Intent(Navigation.this, SettingActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_logout) {
            Intent intent=new Intent(Navigation.this, LoginSelection.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
