package almoder.space.fitnesstrainer;

import android.content.Context;

import java.util.LinkedList;

public class Workout {

    private String title;
    private final LinkedList<Exercise> exes = new LinkedList<>();

    public Workout(String title) {
        title(title);
    }

    public String title() { return title; }

    public void title(String title) { if (title != null) this.title = title; }

    public int count() { return exes.size(); }

    public LinkedList<Exercise> exercises() { return exes; }

    public void addExercise(Context c, int num) {
        exes.add(new Exercise(c, num));
    }

    public void addExercise(Context c, int num, int reps) {
        if (reps != 0) exes.add(new Exercise(c, num, reps));
        else addExercise(c, num);
    }

    public void addExercise(Context c, int num, int reps, int weight) {
        if (weight != 0) exes.add(new Exercise(c, num, reps, weight));
        else addExercise(c, num, reps);
    }

    /*
    public Workout(String title, LinkedList<Exercise> exercises) {
        this.title = title;
        this.exes = exercises;
    }
    */
}
