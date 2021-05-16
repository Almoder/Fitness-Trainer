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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Scanner;

import almoder.space.fitnesstrainer.Exercise;
import almoder.space.fitnesstrainer.R;

public class DescriptionFragment extends Fragment {

    private boolean imgCh = true;
    private int num, reps, weight, imgRes1, imgRes2;
    private int[] ids = { R.id.description_title, R.id.description_type, R.id.description_primer,
        R.id.description_steps, R.id.description_tips, R.id.description_tips_text };

    public DescriptionFragment() {
        this(0);
    }

    public DescriptionFragment(int num) {
        this.num = num;
    }

    public DescriptionFragment(int num, int reps) {
        this(num);
        this.reps = reps;
    }

    public DescriptionFragment(int num, int reps, int weight) {
        this(num, reps);
        this.weight = weight;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        if (savedInstanceState != null) num = savedInstanceState.getInt("num", num);
        Exercise e = new Exercise(view.getContext(), num, reps, weight);
        e.initAll();
        TextView[] tvs = new TextView[6];
        for (int i = 0; i < tvs.length; i++) tvs[i] = view.findViewById(ids[i]);
        for (int i = 0; i < e.params().length; i++) tvs[i].setText(e.params()[i]);
        ImageView iv = view.findViewById(R.id.description_image);
        imgRes1 = e.imgRes1();
        imgRes2 = e.imgRes2();
        if (e.tips() != null) {
            tvs[4].setVisibility(View.VISIBLE);
            tvs[5].setVisibility(View.VISIBLE);
            tvs[5].setText(e.tips());
        }
        if (e.reps() != 0) {
            view.findViewById(R.id.description_cv).setVisibility(View.VISIBLE);
            TextView tv7 = view.findViewById(R.id.description_repeats);
            tv7.setVisibility(View.VISIBLE);
            tv7.append(String.valueOf(e.reps()));
            if (e.weight() != 0) {
                TextView tv8 = view.findViewById(R.id.description_weight);
                tv8.setVisibility(View.VISIBLE);
                tv8.append(String.valueOf(e.weight()));
            }
        }
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
        final Handler handler = new Handler();
        handler.post(new Runnable() {
           @Override
           public void run() {
               if (imgCh) iv.setImageResource(imgRes2);
               else iv.setImageResource(imgRes1);
               imgCh = !imgCh;
               handler.postDelayed(this, 1000);
           }
        });
    }

}
