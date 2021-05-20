package almoder.space.fitnesstrainer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.Locale;

import almoder.space.fitnesstrainer.Exercise;
import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.RVEAdapter;
import almoder.space.fitnesstrainer.SharedPreferencer;

public class ExercisesFragment extends Fragment implements RVEAdapter.OnItemClickListener {

    public interface ExercisesFragmentListener {
        void exItemClicked(int id);
    }

    private ExercisesFragmentListener listener;
    private static final LinkedList<Exercise> list = new LinkedList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if (list.isEmpty()) {
            for (int i = 1; i < 101; i++) list.add(new Exercise(view.getContext(), i));
        }
        recyclerView.setAdapter(new RVEAdapter(list, this));

        //SnapHelper snapHelper = new LinearSnapHelper();
        //snapHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ExercisesFragmentListener)context;
    }

    @Override
    public void onItemClicked(int position) {
        if (listener != null) listener.exItemClicked(list.get(position).num());
        else Toast.makeText(getContext(), "Pos: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.exersises_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort:
                Toast.makeText(getContext(), "Sort", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
