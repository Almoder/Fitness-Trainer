package almoder.space.fitnesstrainer.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.SharedPreferencer;

public class SettingsFragment extends Fragment {

    private Context c;
    private AdapterView.OnItemClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ListView settings = view.findViewById(R.id.settings);
        SharedPreferencer sp = new SharedPreferencer(getContext());
        String[] from = { "s1", "s2" };
        int[] to = { R.id.item_text1, R.id.item_text2 };
        ArrayList<Map<String, Object>> data = new ArrayList<>(2);
        Map<String, Object> m = new HashMap<>();
        m.put("s1", getString(R.string.lang));
        m.put("s2", getString(sp.localeResId()));
        data.add(m);
        m = new HashMap<>();
        m.put("s1", getString(R.string.theme));
        m.put("s2", getString(sp.themeResId()));
        data.add(m);
        settings.setAdapter(new SimpleAdapter(getContext(), data, R.layout.item, from, to));
        settings.setOnItemClickListener(listener);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AdapterView.OnItemClickListener)context;
    }
}
