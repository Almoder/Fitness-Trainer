package almoder.space.fitnesstrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.LinkedList;

public class SharedPreferencer {

    private SharedPreferences sPref;
    private final Context c;
    public LinkedList<WktData> workouts = new LinkedList<>();

    public SharedPreferencer(Context c) {
        this.c = c;
    }

    public boolean addWorkout(WktData wktData) {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        if (count() != workouts.size()) loadWorkouts();
        for (WktData wd : workouts) if (wktData.title().equals(wd.title())) return false;
        workouts.add(wktData);
        saveWorkout(wktData, count());
        sPref.edit().putInt("wktCount", count() + 1).apply();
        return true;
    }

    public void saveWorkout(WktData wktData, int id) {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("w" + id, wktData.title());
        if (wktData.count() != 0) {
            String temp = "w" + id + "e";
            editor.putInt(temp + "Count", wktData.count());
            if (wktData.exercises() != null) for (int i = 0; i < wktData.exercises().size(); i++) {
                Exercise e = wktData.exercises().get(i);
                editor.putInt(temp + i + "num", e.num());
                editor.putInt(temp + i + "reps", e.reps());
                editor.putInt(temp + i + "weight", e.weight());
            }
        } else editor.putInt("w" + id + "eCount", 0);
        editor.apply();
    }

    public void saveWorkouts() {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        for (int i = 0; i < count(); i++) saveWorkout(workouts.get(i), i);
    }

    public void removeWorkout(int id) {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        if (count() != workouts.size()) loadWorkouts();
        if (workouts.remove(id) != null) {
            saveWorkouts();
            sPref.edit().putInt("wktCount", workouts.size()).apply();
        }
    }

    public WktData loadWorkout(int id) {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        WktData ret = new WktData(sPref.getString("w" + id, ""));
        if (ret.title().isEmpty()) return ret;
        String temp = "w" + id + "e";
        for (int i = 0; i < sPref.getInt(temp + "Count", 0); i++) {
            ret.addExercise(c, sPref.getInt(temp + i + "num", 1),
                    sPref.getInt(temp + i + "reps", 1),
                    sPref.getInt(temp + i + "weight", 1));
        }
        Log.d("SharedPreferencer", "id: " + id + ", title: " + ret.title());
        return ret;
    }

    public void loadWorkouts() {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        workouts = new LinkedList<>();
        for (int i = 0, s = 0; s < count() || i < 100; i++) {
            String title = sPref.getString("w" + i, "");
            if (title.equals("")) continue;
            String temp = "w" + i + "e";
            workouts.add(new WktData(sPref.getString("w" + i, "")));
            for (int j = 0; j < sPref.getInt(temp + "Count", 0); j++) {
                workouts.get(i).addExercise(c, sPref.getInt(temp + j + "num", 1),
                        sPref.getInt(temp + j + "reps", 1),
                        sPref.getInt(temp + j + "weight", 1));
            }
            s++;
        }
        Log.d("SharedPreferencer", "Count: " + count());
    }

    private int count() {
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
        return sPref.getInt("wktCount", 0);
    }

    public void localization(String str) {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        sPref.edit().putString("localization", str).apply();
    }

    public String localization() {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sPref.getString("localization", "en");
    }

    public void saveTheme(int resId) {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        sPref.edit().putInt("usedTheme", resId).apply();
    }

    public int loadTheme() {
        sPref = c.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sPref.getInt("usedTheme", R.style.AppTheme);
    }
}
