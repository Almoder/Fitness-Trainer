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

import almoder.space.fitnesstrainer.MainActivity.MainActivity;
import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.SharedPreferencer;

public class SettingsFragment extends Fragment {

    private AdapterView.OnItemClickListener listener1, listener2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ListView appearance = view.findViewById(R.id.appearance),
                 general = view.findViewById(R.id.general);
        SharedPreferencer sp = new SharedPreferencer(getContext());
        String[] from = { "s1", "s2" };
        int[] to = { R.id.item_text1, R.id.item_text2 };
        String[][][] strings1 = new String[][][]{
                {{ "s1", getString(R.string.lang) }, { "s2", sp.localeString() }},
                {{ "s1", getString(R.string.theme) }, { "s2", sp.themeString() }},
                {{ "s1", getString(R.string.text_size) }, { "s2", sp.textSizeString() }}};
        ArrayList<Map<String, Object>> data1 = new ArrayList<>(3);
        for (String[][] a : strings1) {
            Map<String, Object> m = new HashMap<>();
            for (String[] b : a) m.put(b[0], b[1]);
            data1.add(m);
        }
        appearance.setAdapter(new SimpleAdapter(getContext(), data1, R.layout.item, from, to));
        appearance.setOnItemClickListener(listener1);
        String[][][] strings2 = new String[][][]{
                {{ "s1", getString(R.string.unit) }, { "s2", sp.weightUnitString() }}};
        ArrayList<Map<String, Object>> data2 = new ArrayList<>((1));
        for (String[][] a : strings2) {
            Map<String, Object> m = new HashMap<>();
            for (String[] b : a) m.put(b[0], b[1]);
            data2.add(m);
        }
        general.setAdapter(new SimpleAdapter(getContext(), data2, R.layout.item, from, to));
        general.setOnItemClickListener(listener2);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        listener1 = activity.configListener1;
        listener2 = activity.configListener2;
    }
}
