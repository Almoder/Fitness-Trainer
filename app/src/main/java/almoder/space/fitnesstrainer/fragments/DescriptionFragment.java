package almoder.space.fitnesstrainer.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Scanner;

import almoder.space.fitnesstrainer.R;

public class DescriptionFragment extends Fragment {

    private boolean rt = true;
    private int num, imgRes1, imgRes2;

    public DescriptionFragment() {
        this.num = 0;
    }

    public DescriptionFragment(int num) {
        this.num = num;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        if (savedInstanceState != null) num = savedInstanceState.getInt("num", num);
        TextView tv1 = view.findViewById(R.id.description_title);
        TextView tv2 = view.findViewById(R.id.description_type);
        TextView tv3 = view.findViewById(R.id.description_primer);
        TextView tv4 = view.findViewById(R.id.description_steps);
        ImageView iv = view.findViewById(R.id.description_image);
        Scanner s = new Scanner(getResources().openRawResource(getResources().getIdentifier(
                "e" + num, "raw",
                Objects.requireNonNull(getContext()).getPackageName())));
        StringBuilder builder = new StringBuilder();
        while (s.hasNextLine()) builder.append(s.nextLine());
        try {
            JSONObject obj = new JSONObject(builder.toString());
            tv1.setText(obj.getString("title"));
            tv2.setText(obj.getString("type"));
            tv3.setText(obj.getString("primer"));
            JSONArray array = obj.getJSONArray("steps");
            tv4.append(array.getString(0));
            for (int i = 1; i < array.length(); i++) {
                tv4.append("\n");
                tv4.append(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imgRes1 = getResources().getIdentifier(
                "e" + num + "_0", "drawable", getContext().getPackageName());
        imgRes2 = getResources().getIdentifier(
                "e" + num + "_1", "drawable", getContext().getPackageName());
        iv.setImageResource(imgRes1);
        process(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("num", num);
    }

    private void process(View view) {
        final ImageView iv = view.findViewById(R.id.description_image);
        final Handler h = new Handler();
        h.post(new Runnable() {
           @Override
           public void run() {
               if (rt) {
                   iv.setImageResource(imgRes2);
                   rt = false;
               } else {
                   iv.setImageResource(imgRes1);
                   rt = true;
               }
               h.postDelayed(this, 1000);
           }
        });
    }

}
