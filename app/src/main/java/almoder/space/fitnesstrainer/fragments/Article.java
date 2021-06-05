package almoder.space.fitnesstrainer.fragments;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.SharedPreferencer;

public class Article extends Fragment {

    private int id;

    public Article() { this.id = 0; }
    public Article(int id) { this.id = id + 1; }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle sis) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        TextView c = view.findViewById(R.id.article_text);
        if (sis != null) id = sis.getInt("id", 0);
        Scanner scanner = new Scanner(getResources().openRawResource(getResources().getIdentifier(
                "a" + id, "raw", requireContext().getPackageName())));
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) builder.append(scanner.nextLine());
        String string = builder.toString(), text = "";
        int[][] a;
        try {
            JSONObject obj = new JSONObject(string);
            JSONArray headers = obj.getJSONArray("headers");
            JSONArray[] ps = new JSONArray[headers.length()];
            a = new int[headers.length()][2];
            for (int i = 0; i < ps.length; i++) {
                String h = headers.getString(i);
                a[i][0] = text.length(); a[i][1] = a[i][0] + h.length();
                text = text.concat(h);
                ps[i] = obj.getJSONArray("p" + i);
                for (int j = 0; j < ps[i].length(); j++) text = text.concat(ps[i].getString(j));
            }
            SpannableString ss = new SpannableString(text);
            int[] attrs = { android.R.attr.labelTextSize, R.attr.colorAccent/*, R.attr.colorPrimarySurface*/ };
            TypedArray ta = requireContext().obtainStyledAttributes(
                    new SharedPreferencer(getContext()).theme(), attrs);
            for (int[] i : a) setHeader(ss, ta, i[0], i[1]);
            c.setText(ss);
            ta.recycle();
        } catch (JSONException e) {
            e.printStackTrace();
            c.setText(R.string.undefined);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", id);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle sis) {
        super.onViewStateRestored(sis);
        if (sis != null) id = sis.getInt("id", 0);
    }

    @SuppressLint("ResourceType")
    private void setHeader(SpannableString ss, TypedArray ta, int s, int f) {
        if (s == f) return;
        ss.setSpan(new AbsoluteSizeSpan((int)ta.getDimension(0, 17.0f)), s, f, 0);
        ss.setSpan(new ForegroundColorSpan(ta.getColor(1, Color.BLACK)), s, f, 0);
    }
}
