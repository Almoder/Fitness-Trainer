package almoder.space.fitnesstrainer.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import almoder.space.fitnesstrainer.Exercise;
import almoder.space.fitnesstrainer.R;

public class DescriptionFragment extends Fragment {

    private boolean imgCh = true, excAdding = false;
    private int num, reps = 0, weight = 0, imgRes1, imgRes2;
    private Exercise exc = null;
    private View view;
    private final int[] ids = {
            R.id.description_title, R.id.description_type, R.id.description_primer,
            R.id.description_steps, R.id.description_tips, R.id.description_tips_text };

    public DescriptionFragment() {
        this(1);
    }

    public DescriptionFragment(int num) {
        this.num = num;
    }

    public DescriptionFragment(int num, boolean excAdding, Exercise exc) {
        this.num = num;
        this.excAdding = excAdding;
        this.exc = exc;
        if (exc != null) {
            reps = exc.reps();
            weight = exc.weight();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle sis) {
        view = inflater.inflate(R.layout.fragment_description, container, false);
        if (sis != null)  {
            num = sis.getInt("num", num);
            reps = sis.getInt("reps", reps);
            weight = sis.getInt("weight", weight);
            excAdding = sis.getBoolean("excAdding", excAdding);
        }
        if (exc == null) exc = new Exercise(view.getContext(), num, reps, weight);
        num = exc.num();
        exc.initAll();
        TextView[] tvs = new TextView[6];
        for (int i = 0; i < tvs.length; i++) tvs[i] = view.findViewById(ids[i]);
        for (int i = 0; i < exc.params().length; i++) tvs[i].setText(exc.params()[i]);
        ImageView iv = view.findViewById(R.id.description_image);
        imgRes1 = exc.imgRes1();
        imgRes2 = exc.imgRes2();
        if (exc.tips() != null) {
            tvs[4].setVisibility(View.VISIBLE);
            tvs[5].setVisibility(View.VISIBLE);
            tvs[5].setText(exc.tips());
        }
        onResume();
        iv.setImageResource(imgRes1);
        process(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EditText ed1 = view.findViewById(R.id.description_reps_edit);
        if (exc.reps() != 0 || excAdding) {
            view.findViewById(R.id.description_cv).setVisibility(View.VISIBLE);
            TextView tv7 = view.findViewById(R.id.description_reps);
            tv7.setVisibility(View.VISIBLE);
            ed1.setText(String.valueOf(exc.reps()));
        }
        if (exc.weight() != 0 || excAdding) {
            TextView tv8 = view.findViewById(R.id.description_weight);
            tv8.setVisibility(View.VISIBLE);
            EditText ed2 = view.findViewById(R.id.description_weight_edit);
            ed2.setText(String.valueOf(exc.weight()));
            ed2.setVisibility(View.VISIBLE);
        }
        Button button = view.findViewById(R.id.exc_add_button);
        button.setText(excAdding ? R.string.add_to_wkt : R.string.confirm_changes);
        button.setVisibility(ed1.getVisibility());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("num", num);
        outState.putInt("reps", reps);
        outState.putInt("weight", weight);
        outState.putBoolean("excAdding", excAdding);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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
