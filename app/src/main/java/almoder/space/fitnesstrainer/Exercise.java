package almoder.space.fitnesstrainer;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Exercise {

    private int num, imgRes1, imgRes2, reps = 0, weight = 0;
    private String title, primer, type, primary;
    private String equipment, steps, tips, builder;
    private final Context c;

    public Exercise(Context c) {
        this.c = c;
    }

    public Exercise(Context c, int num) {
        this(c);
        Resources res = c.getResources();
        num(num);
        imgRes1(res.getIdentifier(
                "e" + num + "_0", "drawable", c.getPackageName()));
        Scanner s = new Scanner(res.openRawResource(res.getIdentifier(
                "e" + num, "raw", Objects.requireNonNull(c.getPackageName()))));
        StringBuilder builder = new StringBuilder();
        while (s.hasNextLine()) builder.append(s.nextLine());
        this.builder = builder.toString();
        try {
            JSONObject obj = new JSONObject(this.builder);
            title(obj.getString("title"));
            type(obj.getString("type"));
        } catch (JSONException e) { e.printStackTrace(); }
    }

    public Exercise(Context c, int num, int reps) {
        this(c, num);
        reps(reps);
    }

    public Exercise(Context c, int num, int reps, int weight) {
        this(c, num, reps);
        weightUnit(weight);
    }

    public void initAll() {
        imgRes2(c.getResources().getIdentifier(
                "e" + num + "_1", "drawable", c.getPackageName()));
        try {
            JSONObject obj = new JSONObject(builder);
            primer(obj.getString("primer"));
            JSONArray a1 = obj.getJSONArray("primary");
            primary(a1.getString(0));
            for (int i = 1; i < a1.length(); i++) primary(primary.concat("\n" + a1.getString(i)));
        } catch (JSONException e) { e.printStackTrace(); }
        try {
            JSONObject obj = new JSONObject(builder);
            JSONArray a2 = obj.getJSONArray("equipment");
            equipment(a2.getString(0));
            for (int i = 1; i < a2.length(); i++) equipment(equipment.concat("\n" + a2.getString(i)));
        } catch (JSONException e) { e.printStackTrace(); }
        try {
            JSONObject obj = new JSONObject(builder);
            JSONArray a3 = obj.getJSONArray("steps");
            steps(a3.getString(0));
            for (int i = 1; i < a3.length(); i++) steps(steps.concat("\n" + a3.getString(i)));
        } catch (JSONException e) { e.printStackTrace(); }
        try {
            JSONObject obj = new JSONObject(builder);
            JSONArray a4 = obj.getJSONArray("tips");
            tips(a4.getString(0));
            for (int i = 1; i < a4.length(); i++) tips(tips.concat("\n" + a4.getString(i)));
        } catch (JSONException e) { e.printStackTrace(); }

    }

    public List<Exercise> getData(int startPosition, int length) {
        int sp = startPosition + 1, l = length;
        List<Exercise> ret = new ArrayList<>();
        if (sp > 200) return ret;
        if (sp == 200) {
            ret.add(new Exercise(c, 200));
            return ret;
        }
        if (sp + l > 200) l = 200 - sp;
        for (int i = sp; i < sp + l; i++) ret.add(new Exercise(c, i));
        return ret;
    }

    public String[] params() { return new String[]{ title(), type(), primer(), steps() }; }

    public int num() { return num; }
    public void num(int num) { this.num = num; }
    public int imgRes1() { return imgRes1; }
    public void imgRes1(int imgRes1) { this.imgRes1 = imgRes1; }
    public int imgRes2() { return imgRes2; }
    public void imgRes2(int imgRes2) { this.imgRes2 = imgRes2; }
    public int reps() { return reps; }
    public boolean hasReps() { return reps != 0; }
    public void reps(int reps) { this.reps = reps; }
    public int weight() { return weight; }
    public boolean hasWeight() { return weight != 0; }
    public void weight(int weight) {
        boolean r = new SharedPreferencer(c).weightUnit() == R.string.unit_kg;
        this.weight = r ? weight : (int)(weight * 0.45); }
    private void weightUnit(int weight) {
        boolean r = new SharedPreferencer(c).weightUnit() == R.string.unit_kg;
        this.weight = r ? weight : (int)(weight / 0.45);
    }
    public String title() { return title; }
    public void title(String title) { this.title = title; }
    public String type() { return type; }
    public void type(String type) { this.type = type; }
    public String primer() { return primer; }
    public void primer(String primer) { this.primer = primer; }
    public String primary() { return primary; }
    public void primary(String primary) { this.primary = primary; }
    public String equipment() { return equipment; }
    public void equipment(String equipment) { this.equipment = equipment; }
    public String steps() { return steps; }
    public void steps(String steps) { this.steps = steps; }
    public String tips() { return tips; }
    public void tips(String tips) { this.tips = tips; }
}
