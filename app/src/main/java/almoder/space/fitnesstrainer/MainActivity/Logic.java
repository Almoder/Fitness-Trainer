package almoder.space.fitnesstrainer.MainActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import almoder.space.fitnesstrainer.Dialogue;
import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.fragments.AboutFragment;
import almoder.space.fitnesstrainer.fragments.ArticlesFragment;
import almoder.space.fitnesstrainer.fragments.ExercisesFragment;
import almoder.space.fitnesstrainer.fragments.SettingsFragment;
import almoder.space.fitnesstrainer.fragments.WorkoutsFragment;

public class Logic {

    private final Context c;
    private String[] exc_types;
    private static final String repsIsNull = "reps is null!",
                                weightIsNull = "weight is null!";

    public Logic(@Nullable Context c) {
        this.c = c;
        if (c != null) exc_types = c.getResources().getStringArray(R.array.exc_types);
    }

    @SuppressLint("NonConstantResourceId")
    public int getTitleById(int id) {
        switch (id) {
            case R.id.nav_exercise: return R.string.m1;
            case R.id.nav_workouts: return R.string.m2;
            case R.id.nav_articles: return R.string.m3;
            case R.id.nav_settings: return R.string.m4;
            case R.id.nav_aboutapp: return R.string.m6;
            default: return R.string.undefined;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public Fragment getFragmentById(int id) {
        switch (id) {
            case R.id.nav_exercise: return new ExercisesFragment();
            case R.id.nav_workouts: return new WorkoutsFragment();
            case R.id.nav_articles: return new ArticlesFragment();
            case R.id.nav_settings: return new SettingsFragment();
            case R.id.nav_aboutapp:
            default: return new AboutFragment();
        }
    }

    @SuppressLint("NonConstantResourceId")
    public int getItemIdByTitleRes(int id) {
        switch (id) {
            case R.string.m1: return R.id.nav_exercise;
            case R.string.m3: return R.id.nav_articles;
            case R.string.m4: return R.id.nav_settings;
            case R.string.m5: return R.id.nav_share;
            case R.string.m6:
            case R.string.app_name: return R.id.nav_aboutapp;
            case R.string.m2:
            default: return R.id.nav_workouts;
        }
    }

    public AlertDialog getDialogById(int id) {
        if (c == null) throw new IllegalArgumentException();
        if (id == 0) return new Dialogue(c).langDialog();
        if (id == 1) return new Dialogue(c).themeDialog();
        if (id == 2) return new Dialogue(c).textSizeDialog();
        if (id == 3) return new Dialogue(c).weightUnitDialog();
        throw new IllegalArgumentException();
    }

    public int getArticleIdByType(CharSequence type) {
        if (c == null) throw new IllegalArgumentException();
        if (type.equals(exc_types[0])) return 0;
        if (type.equals(exc_types[1])) return 1;
        if (type.equals(exc_types[2])) return 2;
        throw new IllegalArgumentException();
    }

    public int getOnCreateItemId(boolean intentHasExtra, boolean sisIsNull) {
        if (intentHasExtra) return R.id.nav_settings;
        if (sisIsNull) return R.id.nav_aboutapp;
        throw new IllegalArgumentException();
    }

    public int getOnCreateTitle(boolean intentHasExtra, boolean sisIsNull) {
        if (intentHasExtra) return R.string.m4;
        if (sisIsNull) return R.string.app_name;
        throw new IllegalArgumentException();
    }

    public Fragment getOnCreateFragment(boolean intentHasExtra, boolean sisIsNull) {
        if (intentHasExtra) return new SettingsFragment();
        if (sisIsNull) return new AboutFragment();
        throw new IllegalArgumentException();
    }

    public int getOnDescAddClickToast(String reps) {
        if (reps == null) throw new NullPointerException(repsIsNull);
        if (reps.equals("0")) return R.string.reps_not_zero;
        if (reps.isEmpty()) return R.string.reps_not_empty;
        return 0;
    }

    public int getOnDescAddClickToast(String reps, String weight) {
        if (reps == null) throw new NullPointerException(repsIsNull);
        if (weight == null) throw new NullPointerException(weightIsNull);
        if (reps.equals("0") && weight.equals("0")) return R.string.reps_weight_not_zero;
        if (reps.isEmpty() && weight.isEmpty()) return R.string.reps_weight_not_empty;
        if (reps.equals("0")) return R.string.reps_not_zero;
        if (reps.isEmpty()) return R.string.reps_not_empty;
        if (weight.equals("0")) return R.string.weight_not_zero;
        if (weight.isEmpty()) return R.string.weight_not_empty;
        return 0;
    }
}
