package almoder.space.fitnesstrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

import almoder.space.fitnesstrainer.fragments.AboutFragment;
import almoder.space.fitnesstrainer.fragments.DescriptionFragment;
import almoder.space.fitnesstrainer.fragments.ExercisesFragment;
import almoder.space.fitnesstrainer.fragments.WorkoutsFragment;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ExercisesFragment.ExercisesFragmentListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle sis) {
        super.onCreate(sis);
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
        if (sis == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new AboutFragment());
            ft.commit();
        }
        else toolbar.setTitle(sis.getCharSequence("m"));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_exercise:
                ft.replace(R.id.fragment_container, new ExercisesFragment());
                toolbar.setTitle(R.string.m1);
                break;
            case R.id.nav_workouts:
                ft.replace(R.id.fragment_container, new WorkoutsFragment());
                toolbar.setTitle(R.string.m2);
                break;
            case R.id.nav_another:
                Toast.makeText(this, getString(R.string.m3), Toast.LENGTH_SHORT).show();
                toolbar.setTitle(R.string.m3);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_config:
                Toast.makeText(this, getString(R.string.m4), Toast.LENGTH_SHORT).show();
                toolbar.setTitle(R.string.m4);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_share:
                Toast.makeText(this, getString(R.string.m5), Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_about:
                ft.replace(R.id.fragment_container, new AboutFragment());
                toolbar.setTitle(R.string.m6);
                break;
        }
        ft.addToBackStack(null);
        ft.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void itemClicked(int id) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new DescriptionFragment(id));
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("m", getSupportActionBar().getTitle());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle sis) {
        super.onRestoreInstanceState(sis);
        getSupportActionBar().setTitle(sis.getCharSequence("m"));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else getSupportFragmentManager().popBackStack();
    }
}