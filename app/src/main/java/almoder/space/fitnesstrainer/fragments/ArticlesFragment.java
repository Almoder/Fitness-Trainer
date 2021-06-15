package almoder.space.fitnesstrainer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import almoder.space.fitnesstrainer.MainActivity.MainActivity;
import almoder.space.fitnesstrainer.R;

public class ArticlesFragment extends Fragment {

    private AdapterView.OnItemClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ListView lv = view.findViewById(R.id.articles);
        lv.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.articles)));
        lv.setOnItemClickListener(listener);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        listener = activity.articleListener;
    }
}