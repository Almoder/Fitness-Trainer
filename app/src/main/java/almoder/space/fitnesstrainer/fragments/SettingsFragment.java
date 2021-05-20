package almoder.space.fitnesstrainer.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;

import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.SharedPreferencer;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Spinner spinner = view.findViewById(R.id.lang_spinner);
        c = view.getContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.settings_lang));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(new SharedPreferencer(c).localization().equals("en") ? 0 : 1);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferencer sp = new SharedPreferencer(c);
        String[] temp = { "en", "ru" };
        if (!sp.localization().equals(temp[i])) {
            sp.localization(i == 0 ? "en" : "ru");
            Objects.requireNonNull(getActivity()).recreate();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
