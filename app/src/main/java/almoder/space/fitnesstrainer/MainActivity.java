package almoder.space.fitnesstrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ExercisesFragment()).commit();
            nav.setCheckedItem(R.id.nav_exercise);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_exercise:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ExercisesFragment()).commit();
                toolbar.setTitle(R.string.m1);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_workouts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WorkoutsFragment()).commit();
                toolbar.setTitle(R.string.m2);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_another:
                Toast.makeText(this, getString(R.string.m3), Toast.LENGTH_SHORT).show();
                toolbar.setTitle(R.string.m3);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_config:
                Toast.makeText(this, getString(R.string.m4), Toast.LENGTH_SHORT).show();
                toolbar.setTitle(R.string.m4);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_share:
                Toast.makeText(this, getString(R.string.m5), Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}