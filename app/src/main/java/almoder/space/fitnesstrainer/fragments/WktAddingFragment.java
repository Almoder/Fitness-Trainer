package almoder.space.fitnesstrainer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import almoder.space.fitnesstrainer.R;

public class WktAddingFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_adding, container, false);
        if (savedInstanceState != null) {
            EditText editText = view.findViewById(R.id.adding_edit_title);
            editText.setText(savedInstanceState.getString("titleEdit", "Error"));
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText editText = view.findViewById(R.id.adding_edit_title);
        outState.putString("titleEdit", String.valueOf(editText.getText()));
    }
}