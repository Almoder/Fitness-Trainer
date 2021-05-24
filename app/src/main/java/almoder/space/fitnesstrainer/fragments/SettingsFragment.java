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

public class SettingsFragment extends Fragment {

    private Context c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        c = view.getContext();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.settings_langs));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner1 = view.findViewById(R.id.lang_spinner);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferencer sp = new SharedPreferencer(c);
                String[] temp = { "en", "ru" };
                if (!sp.localization().equals(temp[i])) {
                    sp.localization(temp[i]);
                    requireActivity().recreate();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {  }
        });
        spinner1.setSelection(new SharedPreferencer(c).localization().equals("en") ? 0 : 1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.settings_themes));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner2 = view.findViewById(R.id.theme_spinner);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferencer sp = new SharedPreferencer(c);
                int[] temp = { R.style.AppTheme, R.style.DarkTheme };
                if (sp.loadTheme() != temp[i]) {
                    sp.saveTheme(temp[i]);
                    requireActivity().recreate();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {  }
        });
        spinner2.setSelection(new SharedPreferencer(c).loadTheme() == R.style.AppTheme ? 0 : 1);
        return view;
    }
}
