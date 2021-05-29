package almoder.space.fitnesstrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
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

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

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
        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (sis == null) {
            title = R.string.app_name;
            new Fragmentary(getSupportFragmentManager()).replace(new AboutFragment(), title);
            nav.setCheckedItem(R.id.nav_about);
        }
        else if (wktDesc) toolbar.setTitle(m);
        else toolbar.setTitle(title);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        if (!toolbar.getTitle().equals(getString(title))) {
            excAdding = false;
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            new Fragmentary(getSupportFragmentManager()).replace(f, title);
            toolbar.setTitle(title);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void exItemClicked(int id, Exercise exc) {
        excId = id;
        title = R.string.m1;
        toolbar.setTitle(title);
        Fragmentary f = new Fragmentary(getSupportFragmentManager());
        if (wktDesc) f.replace(new DescriptionFragment(id, excAdding,
                new SharedPreferencer(this).loadWorkout(wktId).exercises().get(excId)), title);
        else f.replace(new DescriptionFragment(id, excAdding, exc), title);
    }

    @Override
    public void wkItemClicked(int id, String title) {
        this.title = 0;
        toolbar.setTitle(title); m = title; wktId = id; wktDesc = true;
        new Fragmentary(getSupportFragmentManager())
                .replace(new WktDescFragment(id), "_" + toolbar.getTitle());
    }

    public void onWktAddClick(View view) {
        title = R.string.wkt_adding;
        toolbar.setTitle(title);
        new Fragmentary(getSupportFragmentManager()).replace(new WktAddingFragment(), title);
    }

    public void onExcAddClick(View view) {
        title = R.string.m1;
        excAdding = true; wktDesc = false;
        toolbar.setTitle(title);
        new Fragmentary(getSupportFragmentManager()).replace(new ExercisesFragment(), title);
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
        EditText ed1 = findViewById(R.id.description_reps_edit);
        EditText ed2 = findViewById(R.id.description_weight_edit);
        String reps = String.valueOf(ed1.getText()), weight = String.valueOf(ed2.getText());
        if (reps.equals("0") || weight.equals("0"))
            Toast.makeText(this, "Reps or weight cannot be 0!", Toast.LENGTH_LONG).show();
        else {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            SharedPreferencer sp = new SharedPreferencer(this);
            sp.loadWorkouts();
            if (excAdding) sp.workouts.get(wktId).addExercise(
                        this, excId, Integer.parseInt(reps), Integer.parseInt(weight));
            else if (sp.workouts.get(wktId).count() != 0 && excId < sp.workouts.get(wktId).count()) {
                sp.workouts.get(wktId).exercises().get(excId).reps(Integer.parseInt(reps));
                sp.workouts.get(wktId).exercises().get(excId).weight(Integer.parseInt(weight));
            }
            excAdding = false;
            sp.saveWorkout(sp.workouts.get(wktId), wktId);
            toolbar.setTitle(sp.workouts.get(wktId).title());
            new Fragmentary(getSupportFragmentManager())
                    .replace(new WktDescFragment(wktId), "_" + toolbar.getTitle());
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
        else if (title == 0) {
            title = R.string.m2;
            new Fragmentary(getSupportFragmentManager()).replace(new WorkoutsFragment(), title);
            toolbar.setTitle(title);
        }
        else if (fm.getBackStackEntryCount() > 1) {
            Fragmentary f = new Fragmentary(getSupportFragmentManager());
            if (f.isTempNull()) {
                if (f.popBackStack()) {
                    title = 0;
                    toolbar.setTitle(f.title());
                }
                else {
                    title = f.titleResId();
                    toolbar.setTitle(title);
                }
            }
            else f.popBackStack();
        }
    }
}