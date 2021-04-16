package almoder.space.fitnesstrainer;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class RowData {

    private final int imgRes;
    private String title;
    private String type;

    public RowData(Context c, int num) {
        Resources res = c.getResources();
        title = res.getString(R.string.undefined);
        type = title;
        imgRes = res.getIdentifier(
                "e" + num + "_0", "drawable", c.getPackageName());
        Scanner s = new Scanner(res.openRawResource(res.getIdentifier(
                "e" + num, "raw", c.getPackageName())));
        StringBuilder builder = new StringBuilder();
        while (s.hasNextLine()) builder.append(s.nextLine());
        try {
            JSONObject obj = new JSONObject(builder.toString());
            title = obj.getString("title");
            type = obj.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String title() { return title; }
    public String type() { return type; }
    public int imgRes() { return imgRes; }

}
