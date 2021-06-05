package almoder.space.fitnesstrainer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.SharedPreferencer;

public class SettingsFragment extends Fragment {

    private AdapterView.OnItemClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ListView settings = view.findViewById(R.id.appearance);
        SharedPreferencer sp = new SharedPreferencer(getContext());
        String[] from = { "s1", "s2" };
        String[][][] strings = new String[][][]{
                {{ "s1", getString(R.string.lang) }, { "s2", sp.localeString() }},
                {{ "s1", getString(R.string.theme) }, { "s2", sp.themeString() }},
                {{ "s1", getString(R.string.text_size) }, { "s2", sp.textSizeString() }}};
        int[] to = { R.id.item_text1, R.id.item_text2 };
        ArrayList<Map<String, Object>> data = new ArrayList<>(3);
        for (String[][] a : strings) {
            Map<String, Object> m = new HashMap<>();
            for (String[] b : a) m.put(b[0], b[1]);
            data.add(m);
        }
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
