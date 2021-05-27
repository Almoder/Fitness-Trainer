package almoder.space.fitnesstrainer;

import android.content.Context;

import java.util.LinkedList;

public class WktData {

    private String title;
    private LinkedList<Exercise> exes = new LinkedList<>();

    public WktData(String title) {
        this.title = title;
    }

    public WktData(String title, LinkedList<Exercise> exercises) {
        this.title = title;
        this.exes = exercises;
    }

    public String title() { return title; }
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
}
