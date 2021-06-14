package almoder.space.fitnesstrainer.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import java.util.Locale;

import almoder.space.fitnesstrainer.Exercise;
import almoder.space.fitnesstrainer.Fragmentary;
import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.SharedPreferencer;
import almoder.space.fitnesstrainer.Workout;
import almoder.space.fitnesstrainer.fragments.*;
import static almoder.space.fitnesstrainer.MainActivity.Expressions.*;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ExercisesFragment.ExercisesFragmentListener,
        WorkoutsFragment.WorkoutsFragmentListener,
        AdapterView.OnItemClickListener {

    private Logic logic;
    private Fragmentary fragmentary;
    private SharedPreferencer sp;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private int title = R.string.undefined, workoutId, exerciseId;
    private String titleString;
    private boolean exerciseAdding = false, isWorkout = false;
    private AlertDialog alertDialog;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle sis) {
        sp = new SharedPreferencer(this);
        setTheme(sp.theme());
        super.onCreate(sis);
        setLocale();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        fragmentary = new Fragmentary(getSupportFragmentManager());
        logic = new Logic(this);
        boolean intentHasExtra = getIntent().hasExtra("flag"), sisIsNull = sis == null;
        if (intentHasExtra) getIntent().removeExtra("flag");
        if (intentHasExtra || sisIsNull) {
            title = logic.getOnCreateTitle(intentHasExtra, sisIsNull);
            fragmentary.replace(logic.getOnCreateFragment(intentHasExtra, sisIsNull), title);
            nav.setCheckedItem(logic.getOnCreateItemId(intentHasExtra, sisIsNull));
        }
        if (isWorkout) toolbar.setTitle(titleString);
        else toolbar.setTitle(title);
    }

    private void setLocale() {
        Configuration config = getResources().getConfiguration();
        config.setLocale(new Locale(sp.locale()));
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        sp.hasChanges(false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_share) {
            showToast(R.string.m5);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        title = logic.getTitleById(item.getItemId());
        if (isFragmentOpened(toolbar.getTitle(), getString(title))) return true;
        exerciseAdding = false;
        hideKeyboard();
        if (isFragmentOpened(toolbar.getTitle(), getString(title))) return true;
        exerciseAdding = false;
        fragmentary.replace(logic.getFragmentById(item.getItemId()), title);
        toolbar.setTitle(title);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void exItemClicked(int id, Exercise exc) {
        exerciseId = id;
        title(R.string.m1);
        if (isWorkout) fragmentary.replace(new ExerciseFragment(id, exerciseAdding,
                sp.loadWorkout(workoutId).exercises().get(exerciseId)), title);
        else fragmentary.replace(new ExerciseFragment(id, exerciseAdding, exc), title);
    }

    @Override
    public void wkItemClicked(int id, String title) {
        title(0, title);
        titleString = title;
        workoutId = id;
        isWorkout = true;
        fragmentary.replace(new WorkoutFragment(id), "_" + title);
    }

    public void onWktAddClick(View view) {
        title(R.string.wkt_adding);
        fragmentary.replace(new WorkoutAddingFragment(), title);
    }

    public void onExcAddClick(View view) {
        exerciseAdding = true;
        isWorkout = false;
        title(R.string.m1);
        fragmentary.replace(new ExercisesFragment(), title);
    }

    public void onDoneClick(View view) {
        EditText editText = findViewById(R.id.adding_edit_title);
        String title = String.valueOf(editText.getText());
        if (title.isEmpty()) showToast(R.string.title_is_empty);
        else if (!sp.addWorkout(new Workout(title))) showToast(R.string.workout_exist);
        else { hideKeyboard(); onBackPressed(); }
    }

    public void onExcDoneClick(View view) {
        EditText ed1 = findViewById(R.id.description_reps_edit);
        EditText ed2 = findViewById(R.id.description_weight_edit);
        String reps = String.valueOf(ed1.getText()), weight = String.valueOf(ed2.getText());
        if (!editTextIsVisible(ed2) && showToast(logic.getOnDescAddClickToast(reps)) ||
                showToast(logic.getOnDescAddClickToast(reps, weight))) {
            hideKeyboard();
            sp.loadWorkouts();
            if (exerciseAdding) sp.getWorkout(workoutId).addExercise(
                    this, exerciseId, Integer.parseInt(reps), Integer.parseInt(weight));
            else {
                sp.getWorkout(workoutId).exercises().get(exerciseId).reps(Integer.parseInt(reps));
                sp.getWorkout(workoutId).exercises().get(exerciseId).weight(Integer.parseInt(weight));
            }
            sp.saveWorkout(sp.workouts.get(workoutId), workoutId);
            exerciseAdding = false;
            title(0, sp.workouts.get(workoutId).title());
            fragmentary.replace(new WorkoutFragment(workoutId), "_" + toolbar.getTitle());
        }
    }

    public void onTypeClick(View view) {
        TextView b = (TextView)view;
        if (isWorkout || exerciseAdding) return;
        fragmentary.replace(new Article(logic.getArticleIdByType(b.getText())), title);
        title(R.string.m3);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (title == 0) outState.putString("m", titleString);
        else outState.putInt("title", title);
        outState.putInt("wktId", workoutId);
        outState.putInt("excId", exerciseId);
        outState.putBoolean("excAdding", exerciseAdding);
        outState.putBoolean("wktDesc", isWorkout);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle sis) {
        super.onRestoreInstanceState(sis);
        title = sis.getInt("title", R.string.undefined);
        workoutId = sis.getInt("wktId", 0);
        exerciseId = sis.getInt("excId", 1);
        exerciseAdding = sis.getBoolean("excAdding", false);
        isWorkout = sis.getBoolean("wktDesc", false);
        titleString = sis.getString("m", "Error");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else if (alertDialog != null && alertDialog.isShowing()) alertDialog.cancel();
        else if (title == 0) {
            title(R.string.m2);
            fragmentary.replace(new WorkoutsFragment(), title);
        }
        else if (isWorkoutsEmptyOnBackPress(title, sp.count())) return;
        else if (isBackStackHasEntries(fm.getBackStackEntryCount())) {
            if (fragmentary.popBackStack()) {
                exerciseAdding = false;
                title(0, fragmentary.title());
                fragmentary.replace(new WorkoutFragment(workoutId), "_" + fragmentary.title());
            }
            else title(fragmentary.titleResId());
        }
        nav.setCheckedItem(logic.getItemIdByTitleRes(title));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (isArticlesOpened(title)) { fragmentary.replace(new Article(i), title); return; }
        alertDialog = logic.getDialogById(i);
        alertDialog.show();
        Intent intent = new Intent(this, MainActivity.class).putExtra("flag", true);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) h.postDelayed(this, 1);
                else if (sp.hasChanges()) startActivity(intent);
            }
        }, 1);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    private void title(int resId) { title(resId, getString(resId)); }
    private void title(int resId, String title) { this.title = resId; toolbar.setTitle(title); }
    private void showToast(String text) { Toast.makeText(this, text, Toast.LENGTH_SHORT).show(); }
    private boolean showToast(int resId) {
        if (resId == 0) return true; else { showToast(getString(resId)); return false; }
    }
}