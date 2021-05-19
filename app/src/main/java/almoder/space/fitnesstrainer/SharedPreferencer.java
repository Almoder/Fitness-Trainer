package almoder.space.fitnesstrainer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedList;

public class SharedPreferencer {

    private final SharedPreferences sPref;
    private final Context c;
    public LinkedList<WktData> workouts;

    public SharedPreferencer(Context c) {
        this.c = c;
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
    }

    public void saveWorkout(WktData wktData) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("w" + count(), wktData.title());
        editor.putInt("w" + count() + "eCount", wktData.count());
        editor.putInt("wktCount", count() + 1);
        editor.apply();
    }

    public void removeWorkout(int num) {
        if(workouts.remove(num) != null) {
            SharedPreferences.Editor editor = sPref.edit();
            editor.clear();
            int i = 0;
            for (WktData wd : workouts) {
                editor.putString("w" + i, wd.title());
                String temp = "w" + i + "e";
                editor.putInt(temp + "Count", wd.count());
                int j = 0;
                if (wd.exercises() != null) for (Exercise e : wd.exercises()) {
                    editor.putInt(temp + j + "num", e.num());
                    editor.putInt(temp + j + "reps", e.reps());
                    editor.putInt(temp + j + "weight", e.weight());
                    j++;
                }
                i++;
            }
            editor.putInt("wktCount", i);
            editor.apply();
        }
    }

    public void loadWorkouts() {
        workouts = new LinkedList<>();
        for (int i = 0, s = 0; s < count() || i < 100; i++) {
            String title = sPref.getString("w" + i, "");
            if (title.equals("")) continue;
            String temp = "w" + i + "e";
            workouts.add(new WktData(sPref.getString("w" + i, "")));
            for (int j = 0; j < sPref.getInt(temp + "Count", 0); j++) {
                workouts.get(i).addExercise(c, sPref.getInt(temp + j + "num", 0),
                    sPref.getInt(temp + j + "reps", 0),
                    sPref.getInt(temp + j + "weight", 0));
            }
            s++;
        }
    }

    private int count() { return sPref.getInt("wktCount", 0); }
}
