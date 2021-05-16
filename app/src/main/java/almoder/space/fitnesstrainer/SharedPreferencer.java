package almoder.space.fitnesstrainer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedList;

public class SharedPreferencer {

    private final SharedPreferences sPref;
    private SharedPreferences.Editor editor;
    private final Context c;
    public LinkedList<WktData> workouts;

    public SharedPreferencer(Context c) {
        this.c = c;
        sPref = c.getSharedPreferences("workouts", Context.MODE_PRIVATE);
    }

    public void saveWorkout(WktData wktdata) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("w" + count(), wktdata.title());
        editor.putInt("w" + count() + "eCount", wktdata.count());
        editor.putInt("wktCount", count() + 1);
        editor.apply();
    }

    public void loadWorkouts() {
        workouts = new LinkedList<>();
        for (int i = 0; i < count(); i++) {
            String temp = "w" + i + "e";
            workouts.add(new WktData(sPref.getString("w" + i, "")));
            for (int j = 0; j < sPref.getInt(temp + "Count", 0); j++) {
                workouts.get(i).addExercise(c, sPref.getInt(temp + "e" + j + "num", 0),
                    sPref.getInt(temp + "e" + j + "reps", 0),
                    sPref.getInt(temp + "e" + j + "weight", 0));
            }
        }
    }

    private int count() { return sPref.getInt("wktCount", 0); }

}
