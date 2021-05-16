package almoder.space.fitnesstrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import almoder.space.fitnesstrainer.fragments.AboutFragment;
import almoder.space.fitnesstrainer.fragments.DescriptionFragment;
import almoder.space.fitnesstrainer.fragments.ExercisesFragment;
import almoder.space.fitnesstrainer.fragments.SettingsFragment;
import almoder.space.fitnesstrainer.fragments.WktAddingFragment;
import almoder.space.fitnesstrainer.fragments.WorkoutsFragment;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ExercisesFragment.ExercisesFragmentListener,
        WorkoutsFragment.WorkoutsFragmentListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private int type;

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
            ft.addToBackStack(String.valueOf(toolbar.getTitle())).commit();
        }
        else toolbar.setTitle(sis.getCharSequence("m"));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment f = null;
        switch (item.getItemId()) {
            case R.id.nav_exercise:
                f = new ExercisesFragment();
                type = 0;
                toolbar.setTitle(R.string.m1);
                break;
            case R.id.nav_workouts:
                f = new WorkoutsFragment();
                type = 1;
                toolbar.setTitle(R.string.m2);
                break;
            case R.id.nav_another:
                Toast.makeText(this, getString(R.string.m3), Toast.LENGTH_SHORT).show();
                toolbar.setTitle(R.string.m3);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_config:
                f = new SettingsFragment();
                toolbar.setTitle(R.string.m4);
                break;
            case R.id.nav_share:
                Toast.makeText(this, getString(R.string.m5), Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_about:
                f = new AboutFragment();
                toolbar.setTitle(R.string.m6);
                break;
        }
        if (f != null) {
            ft.replace(R.id.fragment_container, f);
            ft.addToBackStack(String.valueOf(toolbar.getTitle())).commit();
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void exItemClicked(int id) {
        if (type == 0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new DescriptionFragment(id));
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(String.valueOf(toolbar.getTitle())).commit();
        }
    }

    @Override
    public void wkItemClicked(int id) {
        Toast.makeText(this, "Pos: " + id, Toast.LENGTH_SHORT).show();
    }

    public void onAddClick(View view) {
        toolbar.setTitle(getString(R.string.wkt_adding));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new WktAddingFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(String.valueOf(toolbar.getTitle())).commit();
    }

    public void onDoneClick(View view) {
        EditText editText = findViewById(R.id.adding_edit_title);
        if (String.valueOf(editText.getText()).isEmpty())
            Toast.makeText(this, "Title is empty!", Toast.LENGTH_SHORT).show();
        else {
            view.clearFocus();
            new SharedPreferencer(this).saveWorkout(
                    new WktData(String.valueOf(editText.getText())));
            onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("m", Objects.requireNonNull(getSupportActionBar()).getTitle());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle sis) {
        super.onRestoreInstanceState(sis);
        Objects.requireNonNull(getSupportActionBar()).setTitle(sis.getCharSequence("m"));
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
            toolbar.setTitle(fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2).getName());
        }
    }
}