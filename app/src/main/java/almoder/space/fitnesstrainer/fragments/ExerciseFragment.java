package almoder.space.fitnesstrainer.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import almoder.space.fitnesstrainer.SharedPreferencer;

import static almoder.space.fitnesstrainer.fragments.Expressions.*;

public class ExerciseFragment extends Fragment {

    private boolean imgCh = true, excAdding = false;
    private int num;
    private Exercise exc = null;
    private EditText ed1, ed2;
    private final int[] ids = {
            R.id.description_title, R.id.description_type, R.id.description_primer,
            R.id.description_steps, R.id.description_tips, R.id.description_tips_text };

    public ExerciseFragment() {
        this(1);
    }

    public ExerciseFragment(int num) {
        this.num = num;
    }

    public ExerciseFragment(int num, boolean excAdding, Exercise exc) {
        this.num = num;
        this.excAdding = excAdding;
        this.exc = exc;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle sis) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        ed1 = view.findViewById(R.id.description_reps_edit);
        ed2 = view.findViewById(R.id.description_weight_edit);
        String reps = "", weight = "";
        if (sis != null)  {
            num = sis.getInt("num", num);
            exc = new Exercise(getContext(), sis.getInt("num", 1));
            exc.reps(sis.getInt("reps", 0));
            exc.weight(sis.getInt("weight", 0));
            excAdding = sis.getBoolean("excAdding", excAdding);
            reps = sis.getString("repsEditText", "");
            weight = sis.getString("weightEditText", "");
        }
        else if (exc != null) {
            num = exc.num();
        }
        else {
            exc = new Exercise(getContext(), num);
        }
        exc.initAll();
        TextView[] tvs = new TextView[6];
        for (int i = 0; i < tvs.length; i++) {
            tvs[i] = view.findViewById(ids[i]);
        }
        for (int i = 0; i < exc.params().length; i++) {
            tvs[i].setText(exc.params()[i]);
        }
        ImageView iv = view.findViewById(R.id.description_image);
        if (exc.tips() != null) {
            tvs[4].setVisibility(View.VISIBLE);
            tvs[5].setVisibility(View.VISIBLE);
            tvs[5].setText(exc.tips());
        }
        if (exc.hasReps() || excAdding) {
            view.findViewById(R.id.description_cv).setVisibility(View.VISIBLE);
            ed1.setText(reps.equals("") ? String.valueOf(exc.reps()) : reps);
        }
        if (exc.hasWeight() || excAdding) {
            view.findViewById(R.id.description_weight).setVisibility(View.VISIBLE);
            ed2.setVisibility(View.VISIBLE);
            ed2.setText(weight.equals("") ? String.valueOf(exc.weight()) : weight);
            ed2.addTextChangedListener(watcher);
            TextView unit = view.findViewById(R.id.description_unit);
            unit.setVisibility(View.VISIBLE);
            unit.setText(new SharedPreferencer(getContext()).weightUnit());
        }
        Button button = view.findViewById(R.id.exc_add_button);
        button.setText(excAdding ? R.string.add_to_wkt : R.string.confirm_changes);
        button.setVisibility(ed1.getVisibility());
        iv.setImageResource(exc.imgRes1());
        process(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("num", num);
        outState.putInt("reps", exc.reps());
        outState.putInt("weight", exc.weight());
        outState.putBoolean("excAdding", excAdding);
        if (ed1 != null && isRepsEditTextChanged(ed1.getText())) {
            outState.putString("repsEditText", ed1.getText().toString());
        }
        if (ed2 != null && isWeightTextChanged(ed2.getText())) {
            outState.putString("weightEditText", ed2.getText().toString());
        }
    }

    private void process(@NonNull View view) {
        final ImageView iv = view.findViewById(R.id.description_image);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
           @Override
           public void run() {
               if (imgCh) {
                   iv.setImageResource(exc.imgRes2());
               }
               else {
                   iv.setImageResource(exc.imgRes1());
               }
               imgCh = !imgCh;
               handler.postDelayed(this, 1000);
           }
        });
    }

    private final TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                int v = new SharedPreferencer(getContext())
                        .weightUnit() == R.string.unit_kg ? 200 : 400;
                if (Integer.parseInt(charSequence.toString()) > v) {
                    ed2.setText(String.valueOf(v));
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };
}
