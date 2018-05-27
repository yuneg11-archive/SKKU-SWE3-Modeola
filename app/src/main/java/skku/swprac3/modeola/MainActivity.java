package skku.swprac3.modeola;

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
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Views
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ToolBar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Modeola");

        // Floating Action Button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Please Add Action", Toast.LENGTH_SHORT).show();
            }
        });

        //Drawer and Navigation
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_debug1) { // Clear Auth State
            new AuthStateDAL(this).clearAuthState();
            Toast.makeText(this, "Clear Auth State", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_debug2) { // Connect ARTIK
            Intent gotoArtik = new Intent(getApplicationContext(), ArtikConnectActivity.class);
            gotoArtik.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(gotoArtik);
        } else if (id == R.id.nav_debug3) { // Start Service
            Intent serviceStart = new Intent(getApplicationContext(), ArtikNotificationService.class);
            startService(serviceStart);
        } else if (id == R.id.nav_debug4) { // Stop Service
            Intent serviceStop = new Intent(getApplicationContext(), ArtikNotificationService.class);
            stopService(serviceStop);
        } else if (id == R.id.nav_debug5) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
