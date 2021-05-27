package almoder.space.fitnesstrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;
import java.util.Objects;

import almoder.space.fitnesstrainer.fragments.AboutFragment;
import almoder.space.fitnesstrainer.fragments.DescriptionFragment;
import almoder.space.fitnesstrainer.fragments.ExercisesFragment;
import almoder.space.fitnesstrainer.fragments.SettingsFragment;
import almoder.space.fitnesstrainer.fragments.WktAddingFragment;
import almoder.space.fitnesstrainer.fragments.WktDescFragment;
import almoder.space.fitnesstrainer.fragments.WorkoutsFragment;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ExercisesFragment.ExercisesFragmentListener,
        WorkoutsFragment.WorkoutsFragmentListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private int title = R.string.undefined, wktId, excId;
    private String m;
    private NavigationView nav;
    private boolean excAdding = false, wktDesc = false;

    @Override
    protected void onCreate(Bundle sis) {
        setTheme(new SharedPreferencer(this).loadTheme());
        super.onCreate(sis);
        if (sis != null) onRestoreInstanceState(sis);
        Configuration config = getResources().getConfiguration();
        config.setLocale(new Locale(new SharedPreferencer(this).localization()));
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (sis == null) {
            title = R.string.app_name;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, new AboutFragment());
            ft.addToBackStack(String.valueOf(title)).commit();
            nav.setCheckedItem(R.id.nav_about);
        }
        else if (wktDesc) toolbar.setTitle(m);
        else toolbar.setTitle(title);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment f = null;
        switch (item.getItemId()) {
            case R.id.nav_exercise:
                f = new ExercisesFragment();
                title = R.string.m1;
                break;
            case R.id.nav_workouts:
                f = new WorkoutsFragment();
                title = R.string.m2;
                break;
            case R.id.nav_another:
                Toast.makeText(this, getString(R.string.m3), Toast.LENGTH_SHORT).show();
                title = R.string.m3;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_config:
                f = new SettingsFragment();
                title = R.string.m4;
                break;
            case R.id.nav_share:
                Toast.makeText(this, getString(R.string.m5), Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_about:
                f = new AboutFragment();
                title = R.string.m6;
                break;
        }
        if (f != null) {
            if (title != R.string.m1) excAdding = false;
            ft.replace(R.id.container, f);
            ft.addToBackStack(String.valueOf(title)).commit();
            toolbar.setTitle(title);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void exItemClicked(int id, Exercise exc) {
        excId = id;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (wktDesc) ft.replace(R.id.container, new DescriptionFragment(id, excAdding,
                new SharedPreferencer(this).loadWorkout(wktId).exercises().get(excId)));
        else ft.replace(R.id.container, new DescriptionFragment(id, excAdding, exc));
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(String.valueOf(title)).commit();
    }

    @Override
    public void wkItemClicked(int id, String title) {
        toolbar.setTitle(title);
        m = title; wktId = id; wktDesc = true;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new WktDescFragment(id));
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("_" + toolbar.getTitle()).commit();
    }

    public void onWktAddClick(View view) {
        title = R.string.wkt_adding;
        toolbar.setTitle(title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new WktAddingFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(String.valueOf(title)).commit();
    }

    public void onExcAddClick(View view) {
        title = R.string.m1;
        excAdding = true; wktDesc = false;
        toolbar.setTitle(title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new ExercisesFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(String.valueOf(title)).commit();
    }

    public void onDoneClick(View view) {
        EditText editText = findViewById(R.id.adding_edit_title);
        String title = String.valueOf(editText.getText());
        if (title.isEmpty())
            Toast.makeText(this, R.string.title_is_empty, Toast.LENGTH_SHORT).show();
        else if (!new SharedPreferencer(this).addWorkout(new WktData(title)))
            Toast.makeText(this, R.string.workout_exist, Toast.LENGTH_SHORT).show();
        else {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            onBackPressed();
        }
    }

    public void onDescAddClick(View view) {
        Log.d("TAG", "wktId: " + wktId + ", excId: " + excId);
        EditText ed1 = findViewById(R.id.description_reps_edit);
        EditText ed2 = findViewById(R.id.description_weight_edit);
        String reps = String.valueOf(ed1.getText());
        String weight = String.valueOf(ed2.getText());
        if (reps.equals("0") || weight.equals("0"))
            Toast.makeText(this, "Reps or weight cannot be 0!", Toast.LENGTH_LONG).show();
        else {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            SharedPreferencer sp = new SharedPreferencer(this);
            sp.loadWorkouts();
            if (excAdding) sp.workouts.get(wktId).addExercise(
                    this, excId, Integer.parseInt(reps), Integer.parseInt(weight));
            else {
                sp.workouts.get(wktId).exercises().get(excId).reps(Integer.parseInt(reps));
                sp.workouts.get(wktId).exercises().get(excId).weight(Integer.parseInt(weight));
            }
            excAdding = false;
            sp.saveWorkout(sp.workouts.get(wktId), wktId);
            toolbar.setTitle(sp.workouts.get(wktId).title());
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, new WktDescFragment(wktId));
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack("_" + toolbar.getTitle()).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (wktDesc) outState.putString("m", m);
        else outState.putInt("title", title);
        outState.putInt("wktId", wktId);
        outState.putInt("excId", excId);
        outState.putBoolean("excAdding", excAdding);
        outState.putBoolean("wktDesc", wktDesc);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle sis) {
        super.onRestoreInstanceState(sis);
        title = sis.getInt("title", R.string.undefined);
        wktId = sis.getInt("wktId", 0);
        excId = sis.getInt("excId", 1);
        excAdding = sis.getBoolean("excAdding", false);
        wktDesc = sis.getBoolean("wktDesc", false);
        m = sis.getString("m", "Error");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
            String temp = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2).getName();
            if (temp != null && temp.toCharArray()[0] == '_') {
                fm.popBackStack();
                wktDesc = true;
                wkItemClicked(wktId, temp.substring(1));
            }
            else {
                title = Integer.parseInt(Objects.requireNonNull(temp));
                wktDesc = false;
                toolbar.setTitle(title);
                switch (title) {
                    case R.string.m1: nav.setCheckedItem(R.id.nav_exercise); break;
                    case R.string.m2: nav.setCheckedItem(R.id.nav_workouts); break;
                    case R.string.m3: nav.setCheckedItem(R.id.nav_another); break;
                    case R.string.m4: nav.setCheckedItem(R.id.nav_config); break;
                    case R.string.m5: nav.setCheckedItem(R.id.nav_share); break;
                    default: nav.setCheckedItem(R.id.nav_about); break;
                }
            }
        }
    }
}