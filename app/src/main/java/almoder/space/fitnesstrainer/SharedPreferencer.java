package almoder.space.fitnesstrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.LinkedList;

public class SharedPreferencer {

    private SharedPreferences sPref;
    private final Context c;
    public LinkedList<Workout> workouts = new LinkedList<>();

    public SharedPreferencer(Context c) { this.c = c; }

    public boolean addWorkout(Workout workout) {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        if (count() != workouts.size()) loadWorkouts();
        for (Workout wd : workouts) if (workout.title().equals(wd.title())) return false;
        workouts.add(workout);
        saveWorkout(workout, count());
        sPref.edit().putInt("wktCount", count() + 1).apply();
        return true;
    }

    public Workout getWorkout(int id) {
        if (id < 0 || id >= workouts.size()) return null;
        return workouts.get(id);
    }

    public void saveWorkout(Workout workout, int id) {
        if (workout == null) return;
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("w" + id, workout.title());
        if (workout.count() != 0) {
            String temp = "w" + id + "e";
            editor.putInt(temp + "Count", workout.count());
            if (workout.exercises() != null) for (int i = 0; i < workout.exercises().size(); i++) {
                Exercise e = workout.exercises().get(i);
                editor.putInt(temp + i + "num", e.num());
                editor.putInt(temp + i + "reps", e.reps());
                editor.putInt(temp + i + "weight", e.weight());
            }
        } else editor.putInt("w" + id + "eCount", 0);
        editor.apply();
    }

    public void saveWorkouts() {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        sPref.edit().clear().apply();
        for (int i = 0; i < workouts.size(); i++) saveWorkout(workouts.get(i), i);
        sPref.edit().putInt("wktCount", workouts.size()).apply();
    }

    public void removeWorkout(int id) {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        if (count() != workouts.size()) loadWorkouts();
        if (workouts.remove(id) != null) {
            if (workouts.size() != 0) saveWorkouts();
            else sPref.edit().clear().putInt("wktCount", 0).apply();
        }
    }

    public Workout loadWorkout(int id) {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        Workout ret = new Workout(sPref.getString("w" + id, ""));
        if (ret.title().isEmpty()) return ret;
        String temp = "w" + id + "e";
        for (int i = 0; i < sPref.getInt(temp + "Count", 0); i++) {
            ret.addExercise(c, sPref.getInt(temp + i + "num", 1),
                    sPref.getInt(temp + i + "reps", 1),
                    sPref.getInt(temp + i + "weight", 1));
        }
        return ret;
    }

    public void loadWorkouts() {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        workouts = new LinkedList<>();
        for (int i = 0, s = 0; s < count() || i < 100; i++) {
            String title = sPref.getString("w" + i, "");
            if (title.equals("")) continue;
            String temp = "w" + i + "e";
            workouts.add(new Workout(sPref.getString("w" + i, "")));
            for (int j = 0; j < sPref.getInt(temp + "Count", 0); j++) {
                workouts.get(i).addExercise(c, sPref.getInt(temp + j + "num", 1),
                        sPref.getInt(temp + j + "reps", 1),
                        sPref.getInt(temp + j + "weight", 1));
            }
            s++;
        }
        Log.d("SharedPreferencer", "Count: " + count());
    }

    public int count() {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        return sPref.getInt("wktCount", 0);
    }

    public boolean hasChanges() {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sPref.getBoolean("hasChanges", false);
    }

    public void hasChanges(boolean val) {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        sPref.edit().putBoolean("hasChanges", val).apply();
    }

    public void locale(String str) {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        if (locale().equals(str)) return;
        sPref.edit().putString("localization", str).apply();
        hasChanges(true);
    }

    public String locale() {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sPref.getString("localization", "en");
    }

    public int localeResId() { return locale().equals("en") ? R.string.lang_eng : R.string.lang_rus; }

    public String localeString() { return c.getString(localeResId()); }

    public void theme(int themeId, int textSizeId) {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        if (themeId() != themeId || textSizeId() != textSizeId) {
            sPref.edit().putInt("themeId", themeId).putInt("textSizeId", textSizeId).apply();
            hasChanges(true);
        }
    }

    public int theme() {
        if (themeId() == 0) return new int[]{
                R.style.AppTheme_SmallText,
                R.style.AppTheme_DefaultText,
                R.style.AppTheme_LargeText}[textSizeId()];
        else return new int[]{
                R.style.DarkTheme_SmallText,
                R.style.DarkTheme_DefaultText,
                R.style.DarkTheme_LargeText}[textSizeId()];
    }

    public int themeId() {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sPref.getInt("themeId", 0);
    }

    public int textSizeId() {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sPref.getInt("textSizeId", 1);
    }

    public String themeString() {
        if (themeId() == 0) return c.getString(R.string.theme_default);
        else return c.getString(R.string.theme_dark);
    }

    public String textSizeString() {
        return c.getResources().getStringArray(R.array.text_size_entries)[textSizeId()];
    }

    public void weightUnit(int unitResId) {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        if (unitResId == weightUnit()) return;
        sPref.edit().putInt("weightUnit", unitResId).apply();
        hasChanges(true);
    }

    public int weightUnit() {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sPref.getInt("weightUnit", R.string.unit_kg);
    }

    public String weightUnitString() { return c.getString(weightUnit()); }
}
